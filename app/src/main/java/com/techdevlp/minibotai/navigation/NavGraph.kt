package com.techdevlp.minibotai.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.techdevlp.minibotai.more.SetNavigationBarColor
import com.techdevlp.minibotai.more.SetStatusBarColor
import com.techdevlp.minibotai.ui.home.HomeScreenComposable
import com.techdevlp.minibotai.ui.onboarding.OnBoardingComposable
import com.techdevlp.minibotai.ui.splashscreen.SplashScreenComposable

@Composable
fun NavGraph(navController: NavHostController) {
    SetStatusBarColor(
        color = Color.Transparent,
        isIconLight = false
    )
    SetNavigationBarColor(
        color = Color.Transparent,
        isIconLight = false
    )

    NavHost(
        navController = navController,
        startDestination = ScreenNames.SplashScreen.route,
        enterTransition = { enterFadeTransition() },
        exitTransition = { exitFadeTransition() },
        popEnterTransition = { popEnterSlideTransition() },
        popExitTransition = { popExitSlideTransition() }
    ) {
        //Authentication
        composable(route = ScreenNames.SplashScreen.route) {
            SplashScreenComposable(navController = navController)
        }
        composable(route = ScreenNames.OnBoardingScreen.route) {
            OnBoardingComposable(navController = navController)
        }

        //Home
        composable(route = ScreenNames.HomeScreen.route) {
            HomeScreenComposable()
        }
    }
}

val NavHostController.canNavigate: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED