package com.mk.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mk.tasky.agenda.home.presentation.HomeScreen
import com.mk.tasky.authentication.login.presentation.LoginScreen
import com.mk.tasky.authentication.registration.presentation.RegistrationScreen
import com.mk.tasky.core.presentation.TaskyBackground
import com.mk.tasky.navigation.Route
import com.mk.tasky.ui.theme.TaskyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskyTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.LOGIN // TODO: Check if user logged in
                    ) {
                        composable(Route.LOGIN) {
                            TaskyBackground(title = R.string.welcome_back) {
                                LoginScreen(
                                    signupClick = {
                                        navController.navigate(Route.REGISTRATION)
                                    },
                                    onLogin = {
                                        navController.navigate(Route.HOME)
                                    }
                                )
                            }
                        }
                        composable(Route.REGISTRATION) {
                            TaskyBackground(title = R.string.create_your_account) {
                                RegistrationScreen(onBackPress = {
                                    navController.navigateUp()
                                })
                            }
                        }
                        composable(Route.HOME) {
                            TaskyBackground(title = R.string.create_your_account) {
                                HomeScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
