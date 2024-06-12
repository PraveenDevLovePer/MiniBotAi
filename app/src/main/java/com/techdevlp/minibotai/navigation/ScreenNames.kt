package com.techdevlp.minibotai.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenNames(
    val route: String,
    val icon: ImageVector? = null,
    val title: String? = null
) {
    //Authentication
    data object SplashScreen : ScreenNames(route = "splash_screen")
}