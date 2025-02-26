package com.noah.tictactoe.ui.comonents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.noah.tictactoe.GameViewModel
import com.noah.tictactoe.data.PlayerViewModel

@Composable
fun Board(playerViewModel: PlayerViewModel, gameViewModel: GameViewModel, gameID: String) {
    val playerMap = playerViewModel.playerMap.collectAsStateWithLifecycle()
    val gameMap = gameViewModel.gameMap.collectAsStateWithLifecycle()

    val localID = playerViewModel.getLocalPlayerID()

    Column(
        modifier = Modifier
            .height(350.dp)
            .width(350.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF292934))
    ) {
        for (y in 0..2) {
            Spacer(Modifier.weight(1f))
            Row {
                for (x in 0..2) {
                    val coordinates = (y * 3 + x)
                    val mark = when {
                        gameMap.value[gameID]!!.board[coordinates] == 0 -> "X"
                        gameMap.value[gameID]!!.board[coordinates] == 1 -> "O"
                        else -> " "
                    }

                    Spacer(Modifier.weight(1f))
                    Cell(mark) {
                        if((localID == gameMap.value[gameID]!!.players[gameMap.value[gameID]!!.turn])
                            && gameMap.value[gameID]!!.board[coordinates] == 2) {
                            val newBoard = gameMap.value[gameID]!!.board.toMutableList()
                            val turn = gameMap.value[gameID]!!.turn
                            newBoard[coordinates] = turn

                            gameViewModel.makeMove(
                                gameID = gameID,
                                turn = turn,
                                newBoard = newBoard
                            )
                        }
                    }
                }
                Spacer(Modifier.weight(1f))
            }
        }

        Spacer(Modifier.weight(1f))
    }
}