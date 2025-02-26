package com.noah.tictactoe.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.noah.tictactoe.GameViewModel
import com.noah.tictactoe.PlayerViewModel
import com.noah.tictactoe.ui.comonents.Container
import com.noah.tictactoe.ui.comonents.CustomButton

@Composable
fun ResultScreen(navController: NavController, playerViewModel: PlayerViewModel, gameViewModel: GameViewModel, gameID: String) {
    val playerMap = playerViewModel.playerMap.collectAsStateWithLifecycle()
    val gameMap = gameViewModel.gameMap.collectAsStateWithLifecycle()

    val res = when  {
        (gameMap.value[gameID]!!.gameState =="win") && (gameMap.value[gameID]!!.turn == 0) ->
            "${playerMap.value[gameMap.value[gameID]!!.players[1]]!!.name} Won"

        (gameMap.value[gameID]!!.gameState =="win") && (gameMap.value[gameID]!!.turn == 1) ->
            "${playerMap.value[gameMap.value[gameID]!!.players[0]]!!.name} Won"

        (gameMap.value[gameID]!!.gameState =="draw") -> "it's a draw"
        else -> ""
    }

    Container {
        Text(
            text = res,
            style = TextStyle(
                fontSize = 32.sp,
                color = Color.White
            )
        )
        Spacer(Modifier.height(8.dp))
        CustomButton("Go Back") {
            navController.navigate("lobby")
        }
    }
}