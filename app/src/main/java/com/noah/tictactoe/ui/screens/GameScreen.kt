package com.noah.tictactoe.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.noah.tictactoe.GameViewModel
import com.noah.tictactoe.data.PlayerViewModel
import com.noah.tictactoe.ui.comonents.Block
import com.noah.tictactoe.ui.comonents.Board
import com.noah.tictactoe.ui.comonents.Container
import com.noah.tictactoe.ui.comonents.PlayerNameComponent

@Composable
fun GameScreen(navController: NavController, playerViewModel: PlayerViewModel, gameViewModel: GameViewModel, gameID: String) {
    val playerMap = playerViewModel.playerMap.collectAsStateWithLifecycle()
    val gameMap = gameViewModel.gameMap.collectAsStateWithLifecycle()

    LaunchedEffect(gameMap.value) {
        val res = gameViewModel.checkWinner(gameMap.value[gameID]!!.board)

        if(res.isNotEmpty()) {
            gameViewModel.updateGameState(gameID = gameID, gameState = res)
            navController.navigate("result/${gameID}")
        }
    }

    Container {
        Block {
            Row(
                modifier = Modifier.width(350.dp)
            ) {
                PlayerNameComponent(
                    mark = "X",
                    playerName = playerMap.value[gameMap.value[gameID]!!.players[0]]!!.name
                )
                Spacer(Modifier.weight(1f))
                PlayerNameComponent(
                    mark = "O",
                    playerName = playerMap.value[gameMap.value[gameID]!!.players[1]]!!.name
                )
            }
        }

        Block {
            Text(
                text = if(gameMap.value[gameID]!!.turn == 0) playerMap.value[gameMap.value[gameID]!!.players[0]]!!.name
                else playerMap.value[gameMap.value[gameID]!!.players[1]]!!.name ,
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                ),
                modifier = Modifier.padding(32.dp)
            )
        }

        Block {
            Board(playerViewModel, gameViewModel, gameID)
        }
    }
}
