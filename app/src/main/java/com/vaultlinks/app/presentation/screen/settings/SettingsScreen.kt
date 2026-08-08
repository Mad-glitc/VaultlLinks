package com.vaultlinks.app.presentation.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.clickable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Backup
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaultlinks.app.domain.model.ThemeMode
import com.vaultlinks.app.presentation.theme.CategoryPalette
import com.vaultlinks.app.presentation.theme.VaultRadii
import com.vaultlinks.app.presentation.theme.VaultViolet
import com.vaultlinks.app.util.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showPinDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var exportMessage by remember { mutableStateOf<String?>(null) }
    var importError by remember { mutableStateOf<String?>(null) }

    // Copies the picked content:// URI into a temp file (BackupManager works on java.io.File,
    // since it also has to read files written by our own export flow), then hands it to the
    // ViewModel to parse and insert.
    val importLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        runCatching {
            val tempFile = java.io.File(context.cacheDir, "vaultlinks_import_${System.currentTimeMillis()}.json")
            context.contentResolver.openInputStream(uri)?.use { input ->
                tempFile.outputStream().use { output -> input.copyTo(output) }
            } ?: throw java.io.IOException("Could not open the selected file")
            tempFile
        }.onSuccess { file ->
            viewModel.importJson(
                file = file,
                onComplete = { count ->
                    exportMessage = "Imported $count link${if (count == 1) "" else "s"}"
                    file.delete()
                },
                onError = { message ->
                    importError = message
                    file.delete()
                }
            )
        }.onFailure { e ->
            importError = e.message ?: "Couldn't read that file"
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Settings") }) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                SettingsSection(title = "Appearance") {
                    Text("Theme", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(bottom = 8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ThemeMode.entries.forEach { mode ->
                            ThemeModeChip(mode, selected = state.themeMode == mode, onClick = { viewModel.setThemeMode(mode) })
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Accent Color", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(bottom = 8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        CategoryPalette.forEach { color: Color ->
                            val hex = "#%06X".format(0xFFFFFF and color.toArgb())
                            Surface(
                                shape = CircleShape,
                                color = color,
                                modifier = Modifier
                                    .size(32.dp)
                                    .then(Modifier.clickable { viewModel.setAccentColor(hex) })
                            ) {
                                if (state.accentColorHex.equals(hex, ignoreCase = true)) {
                                    androidx.compose.foundation.layout.Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                        Icon(Icons.Filled.CheckCircle, null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                SettingsSection(title = "Security") {
                    SettingsToggleRow(
                        icon = Icons.Outlined.Lock,
                        title = "PIN Lock",
                        subtitle = "Require a PIN to open VaultLinks",
                        checked = state.pinLockEnabled,
                        onCheckedChange = { enabled ->
                            if (enabled) showPinDialog = true else viewModel.setPinLockEnabled(false)
                        }
                    )
                    SettingsToggleRow(
                        icon = Icons.Outlined.Fingerprint,
                        title = "Biometric Unlock",
                        subtitle = "Use fingerprint or face unlock",
                        checked = state.biometricEnabled,
                        onCheckedChange = viewModel::setBiometricEnabled
                    )
                }
            }

            item {
                SettingsSection(title = "Notifications") {
                    SettingsToggleRow(
                        icon = Icons.Outlined.NotificationsNone,
                        title = "Daily Reminder",
                        subtitle = "\"Don't forget to organize today's discoveries\"",
                        checked = state.notificationsEnabled,
                        onCheckedChange = viewModel::setNotificationsEnabled
                    )
                }
            }

            item {
                SettingsSection(title = "Backup & Restore") {
                    SettingsActionRow(
                        icon = Icons.Outlined.Backup,
                        title = "Export as JSON",
                        subtitle = state.lastBackupAt?.let { "Last backup: ${DateUtils.relativeTime(it)}" } ?: "Full backup of all your links",
                        onClick = {
                            viewModel.exportJson { file -> exportMessage = "Exported to ${file.name}" }
                        }
                    )
                    SettingsActionRow(
                        icon = Icons.Outlined.Backup,
                        title = "Export as CSV",
                        subtitle = "Spreadsheet-friendly export",
                        onClick = {
                            viewModel.exportCsv { file -> exportMessage = "Exported to ${file.name}" }
                        }
                    )
                    SettingsActionRow(
                        icon = Icons.Outlined.Restore,
                        title = "Import Backup",
                        subtitle = "Restore from a JSON backup file",
                        onClick = { importLauncher.launch(arrayOf("application/json", "text/plain", "text/*")) }
                    )
                }
            }

            item {
                SettingsSection(title = "About") {
                    SettingsActionRow(
                        icon = Icons.Outlined.PrivacyTip,
                        title = "Privacy Policy",
                        subtitle = "No accounts. No tracking. Fully offline.",
                        onClick = { showPrivacyDialog = true }
                    )
                    SettingsActionRow(
                        icon = Icons.Outlined.Info,
                        title = "App Version",
                        subtitle = "1.0.0",
                        onClick = {}
                    )
                }
            }
        }
    }

    if (showPinDialog) {
        PinSetupDialog(
            onDismiss = { showPinDialog = false },
            onConfirm = { pin ->
                viewModel.setPinLockEnabled(true, pin)
                showPinDialog = false
            }
        )
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy Policy") },
            text = {
                Text(
                    "VaultLinks does not collect, transmit, or sell any personal data. All links, notes, and settings are stored exclusively in a local, encrypted-at-rest database on your device. The only network requests VaultLinks ever makes are to fetch a preview (title, description, image, favicon) for a URL you explicitly save — nothing about you or your vault is ever sent anywhere. No analytics, no ad SDKs, no account system."
                )
            },
            confirmButton = { TextButton(onClick = { showPrivacyDialog = false }) { Text("Got it") } }
        )
    }

    exportMessage?.let { msg ->
        AlertDialog(
            onDismissRequest = { exportMessage = null },
            title = { Text("Backup Complete") },
            text = { Text(msg) },
            confirmButton = { TextButton(onClick = { exportMessage = null }) { Text("OK") } }
        )
    }

    importError?.let { msg ->
        AlertDialog(
            onDismissRequest = { importError = null },
            title = { Text("Import Failed") },
            text = { Text(msg) },
            confirmButton = { TextButton(onClick = { importError = null }) { Text("OK") } }
        )
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(10.dp))
        Card(
            shape = VaultRadii.card,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), content = content)
        }
    }
}

@Composable
private fun SettingsToggleRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange, colors = androidx.compose.material3.SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary))
    }
}

@Composable
private fun SettingsActionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .then(Modifier.clickableRow(onClick)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Column {
            Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun ThemeModeChip(mode: ThemeMode, selected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = VaultRadii.chip,
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.then(Modifier.clickableRow(onClick))
    ) {
        Text(
            mode.label,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun PinSetupDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    val matches = pin.length in 4..8 && pin == confirmPin

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set a PIN") },
        text = {
            Column {
                OutlinedTextField(
                    value = pin, onValueChange = { if (it.length <= 8) pin = it.filter { c -> c.isDigit() } },
                    label = { Text("PIN (4-8 digits)") },
                    visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.NumberPassword),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = confirmPin, onValueChange = { if (it.length <= 8) confirmPin = it.filter { c -> c.isDigit() } },
                    label = { Text("Confirm PIN") },
                    visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.NumberPassword),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(pin) }, enabled = matches) { Text("Set PIN") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
private fun Modifier.clickableRow(onClick: () -> Unit): Modifier =
    this.then(Modifier.clickable(interactionSource = androidx.compose.runtime.remember { androidx.compose.foundation.interaction.MutableInteractionSource() }, indication = null, onClick = onClick))
