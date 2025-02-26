package com.noah.tictactoe.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.noah.tictactoe.Game
import com.noah.tictactoe.GameViewModel
import com.noah.tictactoe.PlayerViewModel
import com.noah.tictactoe.ui.comonents.Block
import com.noah.tictactoe.ui.comonents.Container
import com.noah.tictactoe.ui.comonents.CustomButton

@Composable
fun LobbyScreen(navController: NavController, playerViewModel: PlayerViewModel, gameViewModel: GameViewModel) {
    val playerMap = playerViewModel.playerMap.collectAsStateWithLifecycle()
    val gameMap = gameViewModel.gameMap.collectAsStateWithLifecycle()


    LaunchedEffect(gameMap.value) {
        gameMap.value.forEach { (gameID, game) ->
            if ((playerViewModel.localPlayerId == game.players[0] || playerViewModel.localPlayerId == game.players[1])
                && game.gameState == "playing") {

                navController.navigate("game/${gameID}")
            }
        }
    }

    Container {
        Block {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 64.dp),

                verticalArrangement = Arrangement.Top
            ) {
                items (playerMap.value.entries.toList()) { (playerID, player) ->
                    Row() {
                        Text(
                            text = player.name,
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        )
                        Spacer(Modifier.weight(1f))

                        var hasGame = false
                        gameMap.value.forEach { (gameID, game) ->
                            if(playerViewModel.localPlayerId == game.players[0] && playerID == game.players[1]) {
                                CustomButton(
                                    text = "Cancel Invite",
                                    textStyle = TextStyle(
                                        fontSize = 18.sp
                                    )
                                ) {
                                    gameViewModel.cancelGameInvite(gameID)
                                }
                                hasGame = true
                            } else if(playerViewModel.localPlayerId == game.players[1] && playerID == game.players[0]) {
                                Column {
                                    CustomButton(
                                        text = "Accept Invite",
                                        textStyle = TextStyle(
                                            fontSize = 18.sp
                                        )
                                    ) {
                                        gameViewModel.updateGameState(gameID, "playing")
                                    }
                                    CustomButton(
                                        text = "Decline Invite",
                                        textStyle = TextStyle(
                                            fontSize = 18.sp
                                        )
                                    ) {
                                        gameViewModel.cancelGameInvite(gameID)
                                    }
                                }
                                hasGame = true
                            }
                        }

                        if(!hasGame) {
                            CustomButton(
                                text = "Invite",
                                textStyle = TextStyle(
                                    fontSize = 18.sp
                                )
                            ) {
                                gameViewModel.sendGameInvite(playerViewModel.localPlayerId!!, playerID)
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}