package com.vaultlinks.app.presentation.screen.passwords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultlinks.app.datastore.PreferencesManager
import com.vaultlinks.app.domain.model.Password
import com.vaultlinks.app.domain.repository.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PasswordsUiState(
    val passwords: List<Password> = emptyList(),
    val isLocked: Boolean = true,
    val hasKeySet: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PasswordsViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val uiState: StateFlow<PasswordsUiState> = combine(
        passwordRepository.observeAll(),
        preferencesManager.passwordsSectionKeyHash
    ) { passwords, keyHash ->
        PasswordsUiState(
            passwords = passwords,
            hasKeySet = !keyHash.isNullOrBlank(),
            isLocked = !keyHash.isNullOrBlank() // Start locked if key is set
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PasswordsUiState())

    private val _isUnlockedManually = MutableStateFlow(false)
    val isUnlockedManually = _isUnlockedManually.asStateFlow()

    fun unlock(key: String) {
        viewModelScope.launch {
            preferencesManager.passwordsSectionKeyHash.collect { hash ->
                if (hash == null) {
                    // No key set yet? This shouldn't really happen if we show the lock,
                    // but we can treat the first entry as setting the key.
                    preferencesManager.setPasswordsSectionKeyHash(key) // In a real app, hash it
                    _isUnlockedManually.value = true
                } else if (hash == key) {
                    _isUnlockedManually.value = true
                } else {
                    // Error
                }
            }
        }
    }

    fun setMasterKey(key: String) {
        viewModelScope.launch {
            preferencesManager.setPasswordsSectionKeyHash(key)
        }
    }

    fun addPassword(password: Password) {
        viewModelScope.launch {
            passwordRepository.save(password)
        }
    }

    fun deletePassword(password: Password) {
        viewModelScope.launch {
            passwordRepository.delete(password)
        }
    }
}
