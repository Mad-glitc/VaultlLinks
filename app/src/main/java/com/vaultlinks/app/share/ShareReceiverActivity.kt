package com.vaultlinks.app.share

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vaultlinks.app.MainActivity
import com.vaultlinks.app.presentation.theme.VaultLinksTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Entry point for "Share to VaultLinks" from any other app (Instagram, YouTube, Chrome,
 * Twitter/X, LinkedIn, GitHub, Reddit, ...). Extracts the shared URL and hands off to
 * MainActivity's Save screen — no copy/paste required from the user.
 */
@AndroidEntryPoint
class ShareReceiverActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedUrl = extractUrl(intent)

        setContent {
            VaultLinksTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        if (sharedUrl != null) {
            val forwardIntent = Intent(this, MainActivity::class.java).apply {
                action = ACTION_SAVE_SHARED_URL
                putExtra(EXTRA_SHARED_URL, sharedUrl)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(forwardIntent)
        }
        finish()
    }

    private fun extractUrl(intent: Intent): String? {
        val text = intent.getStringExtra(Intent.EXTRA_TEXT) ?: return null
        val urlRegex = Regex("""https?://\S+""")
        return urlRegex.find(text)?.value
    }

    companion object {
        const val ACTION_SAVE_SHARED_URL = "com.vaultlinks.app.action.SAVE_SHARED_URL"
        const val EXTRA_SHARED_URL = "extra_shared_url"
    }
}
