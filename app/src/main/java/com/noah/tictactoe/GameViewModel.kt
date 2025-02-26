package com.noah.tictactoe

import android.util.Log
import androidx.compose.animation.core.snap
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.lifecycle.ViewModel
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Thread.State

data class Game (
    val board: List<Int> = List(9) { 2 },
    val players: List<String> = listOf(),
    val turn: Int = 0,
    val gameState: String = "",
)

class GameViewModel: ViewModel() {
    private val firestore = Firebase.firestore
    private val _gameMap: MutableStateFlow<MutableMap<String, Game>> = MutableStateFlow( mutableMapOf() )
    val gameMap: StateFlow<MutableMap<String, Game>> = this._gameMap

    init {
        this.getGames()
    }

    fun makeMove(gameID: String, turn: Int, newBoard: List<Int>) {
        val gameUpdate: HashMap<String, Any> = hashMapOf(
            "turn" to if(turn == 0) 1 else 0,
            "board" to newBoard
        )
        this.firestore.collection("games").document(gameID).update(gameUpdate)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }

    //0 1 2
    //3 4 5
    //6 7 8
    fun checkWinner(board: List<Int>): String {
        //Row
        for (i in 0..<3) {
            val rowStart = i * 3
            if(board[rowStart] != 2 && board[rowStart + 1] == board[rowStart] && board[rowStart + 2] == board[rowStart]) {
                return "win"
            }
        }

        //Colum
        for (i in 0..<3) {
           if(board[i] != 2 && board[i] == board[i + 3] && board[i] == board[i + 6]) {
              return "win"
           }
        }


        //Diagonal
        if(board[0] != 2 && board[0] == board[4] && board[0] == board[8]) {
            return "win"
        }

        //Anti-Diagonal
        if(board[2] != 2 && board[2] == board[4] && board[2] == board[6]) {
            return "win"
        }

        //draw
        if(!board.contains(2)) {
            return "draw"
        }

        return ""
    }

    fun sendGameInvite(player1: String, player2: String) {
        val newGame = Game(
            players = listOf(player1, player2),
            gameState = "invite"
        )

        this.firestore.collection("games").add(newGame)
    }

    fun cancelGameInvite(gameID: String) {
        this.firestore.collection("games").document(gameID).delete()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }

    fun updateGameState(gameID: String, gameState: String) {
        val gameUpdate: HashMap<String, Any> = hashMapOf(
            "gameState" to gameState
        )
        this.firestore.collection("games").document(gameID).update(gameUpdate)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
    }

    private fun getGames() {
        this.firestore.collection("games")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.d("debug", "getPlayers: Error, $e")
                    return@addSnapshotListener
                }


                if (snapshots != null) {
                    val newMap = snapshots.documents.associate { doc ->
                        doc.id to doc.toObject(Game::class.java)!!
                    } as MutableMap<String, Game>

                    this._gameMap.value = newMap
                }
            }
    }
}