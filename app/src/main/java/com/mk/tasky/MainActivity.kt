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
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mk.tasky.agenda.detail.reminder.presentation.DetailReminderScreen
import com.mk.tasky.agenda.editor.EditorScreen
import com.mk.tasky.agenda.home.presentation.HomeAgendaType
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
                        MainScreen(startDestination, navController) {
                            mainViewModel.onLogout()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    startDestination: String,
    navController: NavHostController,
    onLogout: () -> Unit
) {
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
            HomeScreen(
                redirect = { type, date ->
                    val route = when (type) {
                        HomeAgendaType.Event -> Route.EVENT
                        HomeAgendaType.Reminder -> Route.REMINDER
                        HomeAgendaType.Task -> Route.TASK
                    }
                    navController.navigate("$route?action=create&date=$date") // TODO: Redirect to Route.DETAIL, and detail checks the type, and depending the type it shows ReminderScreen, EventScreen or TaskScreen
                },
                options = { itemOptions, itemId ->
                    val type = Route.REMINDER // TODO: Make it so it detects based on itemId
                    navController.navigate("$type?action=${itemOptions.name}&id=$itemId")
                },
                onLogout = {
                    onLogout()
                    navController.popBackStack()
                    navController.navigate(Route.LOGIN)
                }
            )
        }
        composable(
            route = Route.REMINDER + "?date={date}&action={action}&id={id}",
            arguments = listOf(
                navArgument("date") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("action") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            val reminderTitle = it.savedStateHandle.get<String>("reminder_title") ?: ""
            val reminderDescription = it.savedStateHandle.get<String>("reminder_description") ?: ""
            DetailReminderScreen(
                reminderTitle = reminderTitle,
                reminderDescription = reminderDescription,
                onClose = {
                    navController.navigateUp()
                },
                openEditor = { id, title, body, size ->
                    navController.navigate(Route.EDITOR + "/$id/$title/$body/$size")
                }
            )
        }

        composable(
            route = Route.EDITOR + "/{text_id}/{title}/{body}/{size}",
            arguments = listOf(
                navArgument("body") {
                    type = NavType.StringType
                },
                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("size") {
                    type = NavType.IntType
                },
                navArgument("text_id") {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString("text_id") ?: ""
            EditorScreen(
                title = it.arguments?.getString("title") ?: "Edit Text",
                onBack = {
                    navController.previousBackStackEntry?.savedStateHandle?.set(id, null)
                    navController.popBackStack()
                },
                onSave = { body ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(id, body)
                    navController.popBackStack()
                },
                textSize = (it.arguments?.getInt("size") ?: 26).sp
            )
        }
    }
}
