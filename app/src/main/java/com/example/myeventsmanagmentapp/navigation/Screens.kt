package com.example.myeventsmanagmentapp.navigation


sealed class Screens(val route: String) {

    data object MainApp : Screens("mainGraph") {
        data object Home : Screens("home_screen")
        data object AddScreen : Screens("add_screen")


    }
    data object Authentication : Screens("authGraph") {
        data object Splash : Screens("splash")
        data object SignUp : Screens("signup_route")
        data object Login : Screens("login_route")
    }
}
