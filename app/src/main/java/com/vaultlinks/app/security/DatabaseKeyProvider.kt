package com.vaultlinks.app.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Generates and stores the passphrase used to encrypt the Room/SQLCipher database file.
 *
 * The passphrase itself is 256 bits of [SecureRandom] output, generated once on first launch
 * and persisted inside [EncryptedSharedPreferences], which in turn wraps its values with a key
 * that lives in the Android Keystore (hardware-backed on most devices) — so the passphrase is
 * never stored in plaintext anywhere on disk, and never leaves the device.
 *
 * This is independent of the optional PIN/biometric app lock in [LockManager]: the database is
 * encrypted at rest regardless of whether the user turns app-lock on, exactly like the app's
 * privacy claims promise.
 */
@Singleton
class DatabaseKeyProvider @Inject constructor(@ApplicationContext private val context: Context) {

    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val encryptedPrefs by lazy {
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /** Returns the existing passphrase, generating and persisting a new one on first call. */
    fun getOrCreatePassphrase(): ByteArray {
        val stored = encryptedPrefs.getString(KEY_PASSPHRASE, null)
        if (stored != null) return decode(stored)

        val newKey = ByteArray(32).also { SecureRandom().nextBytes(it) }
        encryptedPrefs.edit().putString(KEY_PASSPHRASE, encode(newKey)).apply()
        return newKey
    }

    private fun encode(bytes: ByteArray): String = android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)
    private fun decode(str: String): ByteArray = android.util.Base64.decode(str, android.util.Base64.NO_WRAP)

    private companion object {
        const val PREFS_NAME = "vaultlinks_db_key_prefs"
        const val KEY_PASSPHRASE = "db_passphrase"
    }
}
