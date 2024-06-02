package com.example.myeventsmanagmentapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.myeventsmanagmentapp.component.MonthlyHorizontalCalendarView
import com.example.myeventsmanagmentapp.screens.auth.AuthViewModel
import com.example.myeventsmanagmentapp.screens.auth.LoginScreen
import com.example.myeventsmanagmentapp.screens.auth.SignUpScreen
import com.example.myeventsmanagmentapp.screens.auth.SplashScreen
import com.example.myeventsmanagmentapp.screens.task.AddTagDialog
import com.example.myeventsmanagmentapp.screens.task.AddTaskScreen
import com.example.myeventsmanagmentapp.screens.task.CategoryScreen
import com.example.myeventsmanagmentapp.screens.task.Chart5
import com.example.myeventsmanagmentapp.screens.task.Chart6
import com.example.myeventsmanagmentapp.screens.task.HomeScreen
import com.example.myeventsmanagmentapp.screens.task.SettingsScreen
import com.example.myeventsmanagmentapp.screens.task.TaskByDateScreen
import com.example.myeventsmanagmentapp.screens.task.TaskViewModel
import com.example.myeventsmanagmentapp.screens.task.TasksByCategory
import com.example.myeventsmanagmentapp.screens.task.UpdateTaskScreen
import com.google.firebase.auth.FirebaseUser
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random


@Composable
fun EventsAppNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = authViewModel.isSignedIn.value,
    ) {
        authNavigation(navController, authViewModel)
        mainAppNavigation(navController, logout = { authViewModel.logout(context) }) {
            authViewModel.auth.currentUser
        }
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
    navController: NavHostController,
    logout: () -> Unit,
    userName: () -> FirebaseUser?
) {

    navigation(
        startDestination = Screens.MainApp.Home.route,
        route = Screens.MainApp.route,
    ) {
        composable(Screens.MainApp.Home.route) {
            val viewModel: TaskViewModel = hiltViewModel()
            HomeScreen(userName.invoke(), navController, viewModel)
        }

        composable(Screens.MainApp.TaskByDate.route) {
            val viewmodel: TaskViewModel = hiltViewModel()
            TaskByDateScreen(viewmodel, navController)
        }
        composable(Screens.MainApp.CategoryScreen.route) {
            val taskViewModel: TaskViewModel = hiltViewModel()
            CategoryScreen(userName.invoke(), taskViewModel, navController, logout)
        }
        composable(
            Screens.MainApp.AddScreen.route
        ) {
            val viewmodel: TaskViewModel = hiltViewModel()
            viewmodel.taskDate.value = it.savedStateHandle.get<String>("selectedDate").orEmpty()

            AddTaskScreen(navController, viewmodel)
        }
        composable(Screens.MainApp.StaticsScreen.route) {
            val viewmodel: TaskViewModel = hiltViewModel()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    val modelProducer = remember { CartesianChartModelProducer.build() }

                    val x = (1..50).toList()
                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.Default) {
                            modelProducer.tryRunTransaction {
                                lineSeries {
                                    series(
                                        x,
                                        x.map { Random.nextFloat() * 15 })
                                }
                            }
                        }
                    }

                    Chart6(modifier = Modifier.fillMaxWidth().height( 300.dp), viewmodel)
                    Chart5(modifier = Modifier.fillMaxWidth().height( 300.dp), viewmodel)
                }
            }
        }
        dialog(
            Screens.MainApp.DateDialog.route, dialogProperties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {

            MonthlyHorizontalCalendarView(navController) {
                navController.popBackStack()
            }
        }
        dialog(
            Screens.MainApp.AddTagDialog.route, dialogProperties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {
            val addTaskViewModel: TaskViewModel = hiltViewModel()
            AddTagDialog(navController, addTaskViewModel)
        }
        composable("${Screens.MainApp.TaskByCategory.route}/{tagName}", arguments = listOf(
            navArgument("tagName") {
                type = NavType.StringType
            }
        )) { navArgument ->
            val taskViewModel: TaskViewModel = hiltViewModel()

            TasksByCategory(
                navController, taskViewModel, navArgument.arguments?.getString("tagName")
            )
        }
        composable(
            "${Screens.MainApp.UpdateTask.route}/{taskId}", arguments =
            listOf(navArgument("taskId") {
                type = NavType.LongType
            })
        ) {
            val viewmodel: TaskViewModel = hiltViewModel()

            UpdateTaskScreen(navController, viewmodel, it.arguments?.getLong("taskId"), it)
        }

        composable(Screens.MainApp.Settings.route){
            SettingsScreen(navController)
        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}

