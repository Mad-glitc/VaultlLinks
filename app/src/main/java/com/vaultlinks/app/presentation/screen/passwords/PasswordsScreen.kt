package com.vaultlinks.app.presentation.screen.passwords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaultlinks.app.domain.model.Password
import com.vaultlinks.app.presentation.theme.VaultRadii

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordsScreen(viewModel: PasswordsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isUnlockedManually by viewModel.isUnlockedManually.collectAsStateWithLifecycle()

    if (state.hasKeySet && !isUnlockedManually) {
        PasswordsLockScreen(onUnlock = viewModel::unlock)
    } else if (!state.hasKeySet) {
        PasswordsSetupScreen(onSetKey = viewModel::setMasterKey)
    } else {
        PasswordsContentScreen(state.passwords, onAdd = viewModel::addPassword, onDelete = viewModel::deletePassword)
    }
}

@Composable
private fun PasswordsLockScreen(onUnlock: (String) -> Unit) {
    var key by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)) {
            Box(modifier = Modifier.size(84.dp), contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.Lock, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("Passwords Locked", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Enter your section key to continue", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(
            value = key,
            onValueChange = { key = it },
            label = { Text("Section Key") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = VaultRadii.button
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { onUnlock(key) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = VaultRadii.button
        ) {
            Text("Unlock Section")
        }
    }
}

@Composable
private fun PasswordsSetupScreen(onSetKey: (String) -> Unit) {
    var key by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Filled.Key, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(64.dp))
        Spacer(Modifier.height(24.dp))
        Text("Secure Passwords", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Set a key to protect your passwords section. This is separate from your app PIN.", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(
            value = key,
            onValueChange = { key = it },
            label = { Text("Create Section Key") },
            modifier = Modifier.fillMaxWidth(),
            shape = VaultRadii.button
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { onSetKey(key) },
            enabled = key.length >= 4,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = VaultRadii.button
        ) {
            Text("Set Key & Continue")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordsContentScreen(
    passwords: List<Password>,
    onAdd: (Password) -> Unit,
    onDelete: (Password) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Passwords") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Filled.Add, null)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(passwords, key = { it.id }) { password ->
                PasswordCard(password, onDelete = { onDelete(password) })
            }
        }
    }

    if (showAddDialog) {
        AddPasswordDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { onAdd(it); showAddDialog = false }
        )
    }
}

@Composable
private fun AddPasswordDialog(onDismiss: () -> Unit, onConfirm: (Password) -> Unit) {
    var title by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Password") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Service (e.g. Gmail)") })
                OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username/Email") })
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
                OutlinedTextField(value = website, onValueChange = { website = it }, label = { Text("Website (Optional)") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(Password(
                    title = title,
                    username = username,
                    passwordEncrypted = password,
                    website = website,
                    notes = "",
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                ))
            }, enabled = title.isNotBlank() && password.isNotBlank()) {
                Text("Save")
            }
        },
        dismissButton = { androidx.compose.material3.TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
private fun PasswordCard(password: Password, onDelete: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    Card(
        shape = VaultRadii.card,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(password.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(password.username, style = MaterialTheme.typography.bodySmall)
                }
                IconButton(onClick = { visible = !visible }) {
                    Icon(if (visible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility, null)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, null, tint = MaterialTheme.colorScheme.error)
                }
            }
            if (visible) {
                Spacer(Modifier.height(8.dp))
                Text(password.passwordEncrypted, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                if (password.website.isNotBlank()) {
                    Text(password.website, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
