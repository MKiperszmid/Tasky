package com.mk.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
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
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.state.isLoading
        }

        setContent {
            val startDestination = if (mainViewModel.state.isLoggedIn) Route.HOME else Route.LOGIN
            if (!mainViewModel.state.isLoading) {
                TaskyTheme {
                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()
                    Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState) {
                        MainScreen(startDestination, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(startDestination: String, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.LOGIN) {
            TaskyBackground(titleResId = R.string.welcome_back) {
                LoginScreen(
                    signupClick = {
                        navController.navigate(Route.REGISTRATION)
                    },
                    onLogin = {
                        navController.popBackStack()
                        navController.navigate(Route.HOME)
                    }
                )
            }
        }
        composable(Route.REGISTRATION) {
            TaskyBackground(titleResId = R.string.create_your_account) {
                RegistrationScreen(onBackPress = {
                    navController.navigateUp()
                })
            }
        }
        composable(Route.HOME) {
            HomeScreen()
        }
    }
}
