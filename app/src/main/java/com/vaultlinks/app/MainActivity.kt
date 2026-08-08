package com.vaultlinks.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import com.vaultlinks.app.datastore.PreferencesManager
import com.vaultlinks.app.domain.model.ThemeMode
import com.vaultlinks.app.presentation.navigation.Screen
import com.vaultlinks.app.presentation.navigation.VaultNavGraph
import com.vaultlinks.app.presentation.theme.VaultLinksTheme
import com.vaultlinks.app.share.ShareReceiverActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Single-activity host. Uses FragmentActivity (not plain ComponentActivity) because
 * BiometricPrompt requires a FragmentActivity to attach its dialog fragment to.
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var sharedUrl = intent?.takeIf { it.action == ShareReceiverActivity.ACTION_SAVE_SHARED_URL }
            ?.getStringExtra(ShareReceiverActivity.EXTRA_SHARED_URL)

        setContent {
            val themeMode by produceThemeMode()
            val accentColorHex by produceAccentColor()
            var pendingUrl by remember { mutableStateOf(sharedUrl) }

            VaultLinksTheme(themeMode = themeMode, accentColorHex = accentColorHex) {
                VaultNavGraph(
                    startDestination = Screen.Splash.route,
                    pendingSharedUrl = pendingUrl,
                    onSharedUrlConsumed = { pendingUrl = null }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == ShareReceiverActivity.ACTION_SAVE_SHARED_URL) {
            // A fresh share arrived while VaultLinks was already open. Re-launching the
            // activity with a new intent is simplest here — recreate() re-reads getIntent().
            setIntent(intent)
            recreate()
        }
    }

    @androidx.compose.runtime.Composable
    private fun produceThemeMode() = androidx.compose.runtime.produceState(initialValue = ThemeMode.SYSTEM) {
        preferencesManager.themeMode.collect { value = it }
    }

    @androidx.compose.runtime.Composable
    private fun produceAccentColor() = androidx.compose.runtime.produceState(initialValue = "#6C5CE7") {
        preferencesManager.accentColorHex.collect { value = it }
    }
}
