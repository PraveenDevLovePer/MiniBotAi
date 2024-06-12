package com.techdevlp.minibotai.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

const val durationMillis: Int = 1000

fun enterFadeTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(1000))
}

fun exitFadeTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(1000))
}

fun popEnterSlideTransition(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { width -> -width })
}

fun popExitSlideTransition(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { width -> width })
}