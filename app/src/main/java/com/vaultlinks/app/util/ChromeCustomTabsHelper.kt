package com.vaultlinks.app.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

/**
 * Opens saved links using Chrome Custom Tabs rather than an in-app WebView. This keeps
 * VaultLinks out of the business of rendering arbitrary third-party web content (and all
 * the security surface that implies) while still feeling integrated — the tab inherits the
 * app's accent color and offers a one-tap "back to VaultLinks" affordance.
 */
object ChromeCustomTabsHelper {
    fun openUrl(context: Context, url: String, accentColorArgb: Int) {
        val colorParams = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(accentColorArgb)
            .build()
        val intent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(colorParams)
            .setShowTitle(true)
            .setUrlBarHidingEnabled(true)
            .build()
        runCatching {
            intent.launchUrl(context, Uri.parse(url))
        }.onFailure {
            // No browser capable of handling Custom Tabs (rare, e.g. minimal ROMs) — fall
            // back to a plain ACTION_VIEW intent so the link still opens.
            val fallback = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url))
            fallback.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(fallback)
        }
    }
}
