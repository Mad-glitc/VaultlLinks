package com.vaultlinks.app.presentation.screen.pinlock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultlinks.app.datastore.PreferencesManager
import com.vaultlinks.app.security.LockManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PinUnlockUiState(
    val pin: String = "",
    val error: Boolean = false,
    val biometricEnabled: Boolean = false,
    val unlocked: Boolean = false
)

@HiltViewModel
class PinUnlockViewModel @Inject constructor(
    private val lockManager: LockManager,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PinUnlockUiState())
    val uiState: StateFlow<PinUnlockUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(biometricEnabled = preferencesManager.biometricEnabled.first())
        }
    }

    fun onDigit(digit: String) {
        val current = _uiState.value
        if (current.pin.length >= 8) return
        val newPin = current.pin + digit
        _uiState.value = current.copy(pin = newPin, error = false)
        if (newPin.length >= 4) verify(newPin)
    }

    fun onBackspace() {
        _uiState.value = _uiState.value.copy(pin = _uiState.value.pin.dropLast(1), error = false)
    }

    fun onBiometricSuccess() {
        _uiState.value = _uiState.value.copy(unlocked = true)
    }

    private fun verify(pin: String) {
        viewModelScope.launch {
            val correct = lockManager.verifyPin(pin)
            if (correct) {
                _uiState.value = _uiState.value.copy(unlocked = true)
            } else {
                _uiState.value = _uiState.value.copy(error = true, pin = "")
            }
        }
    }
}
