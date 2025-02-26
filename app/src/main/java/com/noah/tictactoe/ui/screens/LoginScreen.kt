package com.noah.tictactoe.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.noah.tictactoe.data.PlayerViewModel
import com.noah.tictactoe.ui.comonents.Block
import com.noah.tictactoe.ui.comonents.Container
import com.noah.tictactoe.ui.comonents.CustomButton


@Composable
fun LoginScreen(navController: NavController, playerViewModel: PlayerViewModel) {
    val playerList = playerViewModel.playerMap.collectAsStateWithLifecycle()

    val textValue = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        Log.d("debug", "LoginScreen: Started")

        if(playerViewModel.getLocalPlayerID() != null) {
            navController.navigate("lobby")
        }
    }

    Container {
        Block {
            Text(
                modifier = Modifier
                    .padding(64.dp),
                text = "TicTacToe",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Block {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),

                value = textValue.value,
                onValueChange = { textValue.value = it },
                singleLine = true,
                label = { Text("Player name")  },

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    unfocusedContainerColor =  MaterialTheme.colorScheme.surfaceContainer,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                ),

                supportingText = { Text( errorMessage.value ) },
            )
        }

        Spacer(Modifier.height(32.dp))

        Block {
            CustomButton(
                text = "Join Lobby"
            ) {
                if (textValue.value.length < 4) {
                    errorMessage.value = "Player name must be at least 4 characters long"
                } else if ( playerList.value.values.any { it.name.equals(textValue.value, ignoreCase = true)}) {
                    errorMessage.value = "Player name already exists"
                } else {
                    playerViewModel.addPlayer(name = textValue.value)
                    navController.navigate("lobby")
                }
            }
        }
    }
}


