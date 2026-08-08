package com.vaultlinks.app.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultlinks.app.data.backup.BackupManager
import com.vaultlinks.app.datastore.PreferencesManager
import com.vaultlinks.app.domain.model.ThemeMode
import com.vaultlinks.app.security.LockManager
import com.vaultlinks.app.worker.WorkScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

private data class QuadPrefs(val theme: ThemeMode, val accent: String, val pin: Boolean, val bio: Boolean)
private data class TriplePrefs(val notif: Boolean, val reminder: Pair<Int, Int>, val lastBackup: Long?)

data class SettingsUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val accentColorHex: String = "#6C5CE7",
    val pinLockEnabled: Boolean = false,
    val biometricEnabled: Boolean = false,
    val notificationsEnabled: Boolean = false,
    val reminderHour: Int = 19,
    val reminderMinute: Int = 0,
    val lastBackupAt: Long? = null,
    val isExporting: Boolean = false,
    val isImporting: Boolean = false,
    val lastExportedFile: File? = null,
    val statusMessage: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val lockManager: LockManager,
    private val backupManager: BackupManager,
    private val workScheduler: WorkScheduler
) : ViewModel() {

    private val _statusMessage = MutableStateFlow<String?>(null)
    private val _isBusy = MutableStateFlow(false to false) // (exporting, importing)
    private val _lastExportedFile = MutableStateFlow<File?>(null)

    val uiState: StateFlow<SettingsUiState> = combine(
        combine(
            preferencesManager.themeMode,
            preferencesManager.accentColorHex,
            preferencesManager.pinLockEnabled,
            preferencesManager.biometricEnabled
        ) { theme, accent, pin, bio -> QuadPrefs(theme, accent, pin, bio) },
        combine(
            preferencesManager.notificationsEnabled,
            preferencesManager.reminderTime,
            preferencesManager.lastBackupAt
        ) { notif, reminder, lastBackup -> TriplePrefs(notif, reminder, lastBackup) }
    ) { quad, triple ->
        SettingsUiState(
            themeMode = quad.theme, accentColorHex = quad.accent, pinLockEnabled = quad.pin, biometricEnabled = quad.bio,
            notificationsEnabled = triple.notif, reminderHour = triple.reminder.first, reminderMinute = triple.reminder.second,
            lastBackupAt = triple.lastBackup
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsUiState())

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch { preferencesManager.setThemeMode(mode) }
    }

    fun setAccentColor(hex: String) {
        viewModelScope.launch { preferencesManager.setAccentColor(hex) }
    }

    fun setPinLockEnabled(enabled: Boolean, pin: String? = null) {
        viewModelScope.launch {
            if (enabled && !pin.isNullOrBlank()) lockManager.setPin(pin) else lockManager.clearPin()
        }
    }

    fun setBiometricEnabled(enabled: Boolean) {
        viewModelScope.launch { preferencesManager.setBiometricEnabled(enabled) }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setNotificationsEnabled(enabled)
            if (enabled) {
                workScheduler.scheduleDailyReminder(uiState.value.reminderHour, uiState.value.reminderMinute)
            } else {
                workScheduler.cancelDailyReminder()
            }
        }
    }

    fun setReminderTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            preferencesManager.setReminderTime(hour, minute)
            if (uiState.value.notificationsEnabled) workScheduler.scheduleDailyReminder(hour, minute)
        }
    }

    fun exportJson(onComplete: (File) -> Unit) {
        viewModelScope.launch {
            val file = backupManager.exportToJson()
            preferencesManager.setLastBackupAt(System.currentTimeMillis())
            onComplete(file)
        }
    }

    fun exportCsv(onComplete: (File) -> Unit) {
        viewModelScope.launch {
            val file = backupManager.exportToCsv()
            onComplete(file)
        }
    }

    fun importJson(file: File, onComplete: (Int) -> Unit, onError: (String) -> Unit = {}) {
        viewModelScope.launch {
            runCatching { backupManager.importFromJson(file) }
                .onSuccess { count -> onComplete(count) }
                .onFailure { e -> onError(e.message ?: "That file isn't a valid VaultLinks backup") }
        }
    }
}
