package com.noah.tictactoe.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.noah.tictactoe.GameViewModel
import com.noah.tictactoe.data.PlayerViewModel
import com.noah.tictactoe.ui.comonents.Block
import com.noah.tictactoe.ui.comonents.Container
import com.noah.tictactoe.ui.comonents.CustomButton
import com.noah.tictactoe.ui.comonents.TopBar

@Composable
fun LobbyScreen(navController: NavController, playerViewModel: PlayerViewModel, gameViewModel: GameViewModel) {
    val playerMap = playerViewModel.playerMap.collectAsStateWithLifecycle()
    val gameMap = gameViewModel.gameMap.collectAsStateWithLifecycle()

    val localID = playerViewModel.getLocalPlayerID()

    LaunchedEffect(gameMap.value) {
        gameMap.value.forEach { (gameID, game) ->
            if ((localID == game.players[0] || localID == game.players[1])
                && game.gameState == "playing") {

                navController.navigate("game/${gameID}")
            }
        }
    }

    Container {
        Block {
            TopBar(text = "Playing as ${playerMap.value[localID]?.name}")
        }
        Block {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 64.dp),

                verticalArrangement = Arrangement.Top
            ) {
                items (playerMap.value.entries.toList()) { (playerID, player) ->
                    if (playerID != localID) {
                        Row(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainer,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(vertical = 16.dp, horizontal = 16.dp),

                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                                if (localID == game.players[0] && playerID == game.players[1] && game.gameState == "invite") {
                                    CustomButton(
                                        text = "Cancel Invite",
                                        textStyle = TextStyle(
                                            fontSize = 18.sp
                                        )
                                    ) {
                                        gameViewModel.cancelGameInvite(gameID)
                                    }
                                    hasGame = true
                                } else if (localID == game.players[1] && playerID == game.players[0] && game.gameState == "invite") {
                                    Column {
                                        CustomButton(
                                            text = "Accept",
                                            textStyle = TextStyle(
                                                fontSize = 18.sp
                                            )
                                        ) {
                                            gameViewModel.updateGameState(gameID, "playing")
                                        }
                                        Spacer(Modifier.height(8.dp))
                                        CustomButton(
                                            text = "Decline",
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

                            if (!hasGame) {
                                CustomButton(
                                    text = "Invite",
                                    textStyle = TextStyle(
                                        fontSize = 18.sp
                                    )
                                ) {
                                    gameViewModel.sendGameInvite(
                                        player1 = localID ?: "",
                                        player2 = playerID
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}