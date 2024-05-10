package com.example.myeventsmanagmentapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.myeventsmanagmentapp.screens.auth.AuthViewModel
import com.example.myeventsmanagmentapp.screens.auth.LoginScreen
import com.example.myeventsmanagmentapp.screens.auth.SignUpScreen
import com.example.myeventsmanagmentapp.screens.auth.SplashScreen

@Composable
fun EventsAppNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = authViewModel.isSignedIn.value,
    ) {

        authNavigation(navController, authViewModel)
        mainAppNavigation(navController)
    }
}


fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    navigation(
        startDestination = Screens.Authentication.Splash.route,
        route = Screens.Authentication.route,
    ) {
        composable(Screens.Authentication.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screens.Authentication.SignUp.route) {
            SignUpScreen(navController, authViewModel)
        }

        composable(Screens.Authentication.Login.route) {
            LoginScreen(navController, authViewModel)
        }
    }
}


fun NavGraphBuilder.mainAppNavigation(
    navController: NavHostController
) {
    navigation(
        startDestination = Screens.MainApp.Home.route,
        route = Screens.MainApp.route,
    ) {
        composable(Screens.MainApp.Home.route) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .clickable {
                    navController.navigate(Screens.MainApp.AddScreen.route)
                }) {

            }
        }

        composable(Screens.MainApp.AddScreen.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ) {

            }
        }

    }
}