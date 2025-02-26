package com.noah.tictactoe

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noah.tictactoe.data.PlayerViewModel
import com.noah.tictactoe.data.PreferenceManager
import com.noah.tictactoe.ui.screens.GameScreen
import com.noah.tictactoe.ui.screens.LobbyScreen
import com.noah.tictactoe.ui.screens.LoginScreen
import com.noah.tictactoe.ui.screens.ResultScreen

@Composable
fun Application() {
    val context = LocalContext.current
    val preferenceManager = PreferenceManager(context)
    val playerViewModel = PlayerViewModel(preferenceManager)
    val gameViewModel = GameViewModel()

    val navController = rememberNavController()

    NavHost(navController, startDestination = "join") {
        composable(route = "join") {
            LoginScreen(navController, playerViewModel)
        }

        composable(route = "lobby") {
            LobbyScreen(navController, playerViewModel, gameViewModel)
        }

        composable(route = "game/{gameID}") { backStackEntry ->
            val gameID = backStackEntry.arguments?.getString("gameID")

            if (gameID != null) {
                GameScreen(navController, playerViewModel, gameViewModel, gameID)
            }
        }

        composable(route = "result/{gameID}") { navBackStackEntry ->
            val gameID = navBackStackEntry.arguments?.getString("gameID")

            if (gameID != null) {
                ResultScreen(navController, playerViewModel, gameViewModel, gameID)
            }
        }
    }
}
