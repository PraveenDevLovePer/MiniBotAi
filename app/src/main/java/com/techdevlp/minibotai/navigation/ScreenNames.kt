package com.techdevlp.minibotai.navigation

sealed class ScreenNames(
    val route: String
) {
    //Authentication
    data object SplashScreen : ScreenNames(route = "splash_screen")
    data object OnBoardingScreen : ScreenNames(route = "onboarding_screen")

    //Home
    data object HomeScreen : ScreenNames(route = "home_screen")

}