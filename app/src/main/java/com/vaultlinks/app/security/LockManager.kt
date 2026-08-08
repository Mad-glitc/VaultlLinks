package com.vaultlinks.app.security

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.vaultlinks.app.datastore.PreferencesManager
import kotlinx.coroutines.flow.first
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Local-only app lock. The PIN is never stored in plaintext — only its SHA-256 hash is
 * persisted in DataStore — and biometric auth is delegated entirely to the platform
 * BiometricPrompt / Keystore, so no biometric data ever touches app code.
 */
@Singleton
class LockManager @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    suspend fun isLockEnabled(): Boolean =
        preferencesManager.pinLockEnabled.first() || preferencesManager.biometricEnabled.first()

    suspend fun setPin(pin: String) {
        preferencesManager.setPinHash(hash(pin))
        preferencesManager.setPinLockEnabled(true)
    }

    suspend fun clearPin() {
        preferencesManager.setPinHash(null)
        preferencesManager.setPinLockEnabled(false)
    }

    suspend fun verifyPin(pin: String): Boolean {
        val stored = preferencesManager.pinHash.first() ?: return false
        return stored == hash(pin)
    }

    fun isBiometricAvailable(activity: FragmentActivity): Boolean {
        val manager = BiometricManager.from(activity)
        return manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) ==
            BiometricManager.BIOMETRIC_SUCCESS
    }

    fun showBiometricPrompt(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val executor = androidx.core.content.ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                onError(errString.toString())
            }
        }
        val prompt = BiometricPrompt(activity, executor, callback)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock VaultLinks")
            .setSubtitle("Use your fingerprint or face to continue")
            .setNegativeButtonText("Use PIN instead")
            .build()
        prompt.authenticate(promptInfo)
    }

    private fun hash(value: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(value.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
