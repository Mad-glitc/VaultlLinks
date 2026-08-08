package com.vaultlinks.app.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.vaultlinks.app.domain.model.ThemeMode

private fun getLightColors(accent: Color) = lightColorScheme(
    primary = accent,
    onPrimary = PorcelainSurface,
    primaryContainer = accent.copy(alpha = 0.12f),
    onPrimaryContainer = accent,
    secondary = VaultAmber,
    onSecondary = InkPrimary,
    background = PorcelainBg,
    onBackground = InkPrimary,
    surface = PorcelainSurface,
    onSurface = InkPrimary,
    surfaceVariant = PorcelainSurfaceVariant,
    onSurfaceVariant = InkSecondary,
    outline = HairlineLight,
    error = DangerRed
)

private fun getDarkColors(accent: Color) = darkColorScheme(
    primary = accent,
    onPrimary = VoidBg,
    primaryContainer = accent.copy(alpha = 0.2f),
    onPrimaryContainer = accent,
    secondary = VaultAmber,
    onSecondary = InkPrimary,
    background = VoidBg,
    onBackground = MoonPrimary,
    surface = VoidSurface,
    onSurface = MoonPrimary,
    surfaceVariant = VoidSurfaceVariant,
    onSurfaceVariant = MoonSecondary,
    outline = HairlineDark,
    error = DangerRed
)

@Composable
fun VaultLinksTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    accentColorHex: String = "#6C5CE7",
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemDark = isSystemInDarkTheme()
    val useDark = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> systemDark
    }

    val accentColor = runCatching { Color(android.graphics.Color.parseColor(accentColorHex)) }
        .getOrDefault(VaultViolet)

    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            if (useDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        useDark -> getDarkColors(accentColor)
        else -> getLightColors(accentColor)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = VaultTypography,
        shapes = VaultShapes,
        content = content
    )
}
