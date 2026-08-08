package com.vaultlinks.app.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val VaultShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

// Extra radii used outside the Shapes() slots (cards, sheets, chips) for finer control.
object VaultRadii {
    val card = RoundedCornerShape(20.dp)
    val cardLarge = RoundedCornerShape(28.dp)
    val chip = RoundedCornerShape(50)
    val sheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    val button = RoundedCornerShape(16.dp)
}
