package org.dandowney.bestpartofmyday.design.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

// region Raw colors

val Red300 = Color(0xFFE57373)

val Pink300 = Color(0xFFF06292)

val Purple200 = Color(0xFFBB86FC)
val Purple300 = Color(0xFFBA68C8)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)

val DeepPurple300 = Color(0xFF9575CD)

val Indigo300 = Color(0xFF7986CB)

val Blue300 = Color(0xFF64B5F6)

val LightBlue300 = Color(0xFF4FC3F7)

val Cyan300 = Color(0xFF4DD0E1)

val Teal200 = Color(0xFF03DAC5)
val Teal300 = Color(0xFF4DB6AC)

val Green300 = Color(0xFF81C784)

val LightGreen300 = Color(0xFFAED581)

val Lime300 = Color(0xFFDCE775)

val Yellow300 = Color(0xFFFFF176)

val Amber300 = Color(0xFFFFD54F)

val Orange300 = Color(0xFFFFB74D)

val DeepOrange300 = Color(0xFFFF8A65)

val Brown300 = Color(0xFFA1887F)

val Gray100 = Color(0XFFF5F5F5)
val Gray200 = Color(0xFFEEEEEE)
val Gray300 = Color(0xFFE0E0E0)
val Gray400 = Color(0xFFBDBDBD)
val Gray500 = Color(0xFF9E9E9E)
val Gray600 = Color(0xFF757575)
val Gray700 = Color(0xFF616161)
val Gray800 = Color(0xFF424242)
val Gray900 = Color(0xFF212121)

val BlueGray300 = Color(0xFF90A4AE)

// endregion

// region Themed colors

val textDark = Gray100
val textLight = Gray800

val placeholderTextDark = Gray200
val placeholderTextLight = Gray700

// endregion

// region Color extensions

fun Color.isDark(): Boolean = luminance() < 0.5

fun Color.isAlmostBlack(): Boolean = luminance() < 0.2

fun Color.isAlmostWhite(): Boolean = luminance() > 0.8

// endregion
