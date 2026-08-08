package com.vaultlinks.app.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * VaultLinks design tokens — "Nexus Violet".
 *
 * Signature: an indigo-to-violet gradient (used sparingly, on the FAB, save-confirmation
 * flash, and stat highlights) paired with a warm amber for priority/favorite accents, so the
 * two most "alive" interactions in the app — saving and favoriting — get distinct temperatures.
 * Everything else stays quiet: neutral ink-on-porcelain in light mode, true near-black
 * surfaces in dark mode so OLED screens stay inky and cards can glow via elevation, not glare.
 */

// Brand
val VaultVioletDeep = Color(0xFF4834D4)
val VaultViolet = Color(0xFF6C5CE7)
val VaultVioletLight = Color(0xFFA29BFE)
val VaultAmber = Color(0xFFFDB44B)
val VaultAmberDeep = Color(0xFFE67E22)

// Light surfaces
val PorcelainBg = Color(0xFFFAFAFC)
val PorcelainSurface = Color(0xFFFFFFFF)
val PorcelainSurfaceVariant = Color(0xFFF1F0F7)
val InkPrimary = Color(0xFF17161D)
val InkSecondary = Color(0xFF5C5A6B)
val HairlineLight = Color(0xFFE7E5F0)

// Dark surfaces
val VoidBg = Color(0xFF0C0B10)
val VoidSurface = Color(0xFF17151E)
val VoidSurfaceVariant = Color(0xFF211F2B)
val MoonPrimary = Color(0xFFF4F3F8)
val MoonSecondary = Color(0xFFA9A6BC)
val HairlineDark = Color(0xFF2B2836)

// Semantic
val SuccessGreen = Color(0xFF2ECC71)
val DangerRed = Color(0xFFE74C3C)
val InfoBlue = Color(0xFF3498DB)

// Category accent palette (used when a category has no custom color)
val CategoryPalette = listOf(
    Color(0xFF6C5CE7), Color(0xFF00B894), Color(0xFFE17055), Color(0xFF0984E3),
    Color(0xFFFD79A8), Color(0xFFFDCB6E), Color(0xFF00CEC9), Color(0xFFE84393)
)
