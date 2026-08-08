package com.vaultlinks.app.presentation.screen.splash

import androidx.lifecycle.ViewModel
import com.vaultlinks.app.datastore.PreferencesManager
import com.vaultlinks.app.security.LockManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

data class InitialState(val onboardingDone: Boolean, val requiresLock: Boolean)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val lockManager: LockManager
) : ViewModel() {

    suspend fun resolveInitialState(): InitialState {
        val onboardingDone = preferencesManager.onboardingDone.first()
        val requiresLock = lockManager.isLockEnabled()
        return InitialState(onboardingDone, requiresLock)
    }
}
