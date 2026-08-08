package com.vaultlinks.app.presentation.screen.pinlock

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaultlinks.app.security.LockManager

@Composable
fun PinUnlockScreen(
    onUnlocked: () -> Unit,
    viewModel: PinUnlockViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context as? FragmentActivity
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(state.unlocked) {
        if (state.unlocked) onUnlocked()
    }

    LaunchedEffect(state.error) {
        if (state.error) {
            repeat(3) {
                shakeOffset.animateTo(12f, tween(50))
                shakeOffset.animateTo(-12f, tween(50))
            }
            shakeOffset.animateTo(0f, tween(50))
        }
    }

    // Offer biometric automatically the moment the lock screen appears, if it's configured —
    // saves the user a tap in the common case. They can still fall back to the PIN pad.
    LaunchedEffect(state.biometricEnabled, activity) {
        if (state.biometricEnabled && activity != null) {
            triggerBiometricPrompt(activity, onSuccess = viewModel::onBiometricSuccess)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(0.15f))
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)) {
            Box(modifier = Modifier.size(84.dp), contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.Lock, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("Enter your PIN", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(
            if (state.error) "Incorrect PIN, try again" else "Unlock VaultLinks to continue",
            style = MaterialTheme.typography.bodyLarge,
            color = if (state.error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(48.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.offset(x = shakeOffset.value.dp)
        ) {
            repeat(6) { index ->
                val filled = index < state.pin.length
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            if (filled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                            CircleShape
                        )
                )
            }
        }

        Spacer(Modifier.weight(0.1f))

        NumericKeypad(
            onDigit = viewModel::onDigit,
            onBackspace = viewModel::onBackspace,
            showBiometric = state.biometricEnabled && activity != null,
            onBiometricClick = {
                activity?.let { fa ->
                    triggerBiometricPrompt(fa, onSuccess = viewModel::onBiometricSuccess)
                }
            }
        )
        Spacer(Modifier.weight(0.15f))
    }
}

@Composable
private fun NumericKeypad(
    onDigit: (String) -> Unit,
    onBackspace: () -> Unit,
    showBiometric: Boolean,
    onBiometricClick: () -> Unit
) {
    val rows = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9")
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                row.forEach { digit -> KeypadButton(label = digit, onClick = { onDigit(digit) }) }
            }
            Spacer(Modifier.height(20.dp))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(32.dp), verticalAlignment = Alignment.CenterVertically) {
            if (showBiometric) {
                IconButton(onClick = onBiometricClick, modifier = Modifier.size(72.dp)) {
                    Icon(Icons.Filled.Fingerprint, contentDescription = "Use biometrics", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                }
            } else {
                Spacer(Modifier.size(72.dp))
            }
            KeypadButton(label = "0", onClick = { onDigit("0") })
            IconButton(onClick = onBackspace, modifier = Modifier.size(72.dp)) {
                Icon(Icons.Outlined.Backspace, contentDescription = "Backspace", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(28.dp))
            }
        }
    }
}

@Composable
private fun KeypadButton(label: String, onClick: () -> Unit) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .size(72.dp)
            .then(Modifier.clickableCenter(onClick))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(label, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun Modifier.clickableCenter(onClick: () -> Unit): Modifier =
    this.then(
        Modifier.clickable(
            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    )

/** Fires the platform BiometricPrompt via Hilt's application-level entry point (this Composable
 *  isn't itself in the Hilt graph, so we reach the singleton [LockManager] this way). */
private fun triggerBiometricPrompt(activity: FragmentActivity, onSuccess: () -> Unit) {
    val entryPoint = dagger.hilt.android.EntryPointAccessors.fromApplication(
        activity.applicationContext,
        PinUnlockEntryPoint::class.java
    )
    entryPoint.lockManager().showBiometricPrompt(
        activity = activity,
        onSuccess = onSuccess,
        onError = { /* user cancelled or failed — they can retry via the fingerprint button or use the PIN */ }
    )
}

@dagger.hilt.EntryPoint
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent::class)
private interface PinUnlockEntryPoint {
    fun lockManager(): LockManager
}
