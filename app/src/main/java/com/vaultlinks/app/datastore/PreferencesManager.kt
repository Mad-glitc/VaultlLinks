package com.vaultlinks.app.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vaultlinks.app.domain.model.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "vaultlinks_prefs")

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val ACCENT_COLOR = stringPreferencesKey("accent_color")
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val PIN_LOCK_ENABLED = booleanPreferencesKey("pin_lock_enabled")
        val PIN_HASH = stringPreferencesKey("pin_hash")
        val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val REMINDER_HOUR = intPreferencesKey("reminder_hour")
        val REMINDER_MINUTE = intPreferencesKey("reminder_minute")
        val LAST_BACKUP_AT = longPreferencesKey("last_backup_at")
        val DEFAULT_VIEW_GRID = booleanPreferencesKey("default_view_grid")
        val PASSWORDS_SECTION_KEY_HASH = stringPreferencesKey("passwords_section_key_hash")
    }

    val themeMode: Flow<ThemeMode> = context.dataStore.data.map { prefs ->
        runCatching { ThemeMode.valueOf(prefs[Keys.THEME_MODE] ?: ThemeMode.SYSTEM.name) }
            .getOrDefault(ThemeMode.SYSTEM)
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { it[Keys.THEME_MODE] = mode.name }
    }

    val accentColorHex: Flow<String> = context.dataStore.data.map { it[Keys.ACCENT_COLOR] ?: "#6C5CE7" }

    suspend fun setAccentColor(hex: String) {
        context.dataStore.edit { it[Keys.ACCENT_COLOR] = hex }
    }

    val onboardingDone: Flow<Boolean> = context.dataStore.data.map { it[Keys.ONBOARDING_DONE] ?: false }

    suspend fun setOnboardingDone(done: Boolean) {
        context.dataStore.edit { it[Keys.ONBOARDING_DONE] = done }
    }

    val pinLockEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.PIN_LOCK_ENABLED] ?: false }

    suspend fun setPinLockEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.PIN_LOCK_ENABLED] = enabled }
    }

    suspend fun setPinHash(hash: String?) {
        context.dataStore.edit {
            if (hash == null) it.remove(Keys.PIN_HASH) else it[Keys.PIN_HASH] = hash
        }
    }

    val pinHash: Flow<String?> = context.dataStore.data.map { it[Keys.PIN_HASH] }

    val biometricEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.BIOMETRIC_ENABLED] ?: false }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.BIOMETRIC_ENABLED] = enabled }
    }

    val notificationsEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.NOTIFICATIONS_ENABLED] ?: false }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.NOTIFICATIONS_ENABLED] = enabled }
    }

    val reminderTime: Flow<Pair<Int, Int>> = context.dataStore.data.map {
        (it[Keys.REMINDER_HOUR] ?: 19) to (it[Keys.REMINDER_MINUTE] ?: 0)
    }

    suspend fun setReminderTime(hour: Int, minute: Int) {
        context.dataStore.edit {
            it[Keys.REMINDER_HOUR] = hour
            it[Keys.REMINDER_MINUTE] = minute
        }
    }

    val lastBackupAt: Flow<Long?> = context.dataStore.data.map { it[Keys.LAST_BACKUP_AT] }

    suspend fun setLastBackupAt(timestamp: Long) {
        context.dataStore.edit { it[Keys.LAST_BACKUP_AT] = timestamp }
    }

    val defaultViewGrid: Flow<Boolean> = context.dataStore.data.map { it[Keys.DEFAULT_VIEW_GRID] ?: true }

    suspend fun setDefaultViewGrid(grid: Boolean) {
        context.dataStore.edit { it[Keys.DEFAULT_VIEW_GRID] = grid }
    }

    val passwordsSectionKeyHash: Flow<String?> = context.dataStore.data.map { it[Keys.PASSWORDS_SECTION_KEY_HASH] }

    suspend fun setPasswordsSectionKeyHash(hash: String?) {
        context.dataStore.edit {
            if (hash == null) it.remove(Keys.PASSWORDS_SECTION_KEY_HASH)
            else it[Keys.PASSWORDS_SECTION_KEY_HASH] = hash
        }
    }
}
