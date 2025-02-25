package com.noah.tictactoe

import android.util.Log
import androidx.compose.animation.core.snap
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

@IgnoreExtraProperties
data class Player(
    @get:Exclude val id: String = "",
    val name: String,
)

@Entity
data class LocalPlayer(
    val id: String,
    val name: String,
)

class PlayerViewModel: ViewModel() {
    private val firestore = Firebase.firestore
    private val _playerMap: MutableStateFlow<MutableMap<Int, Player>> = MutableStateFlow( mutableMapOf(
        0 to Player(id = "2dsVssdQg2DdQoDn6520", name = "Noah")
    ))
    val playerMap: StateFlow<Map<Int, Player>> = this._playerMap


    init {
        this.getPlayers()
    }

    fun addPlayer(name: String) {
        val newPlayer = Player(
            name = name
        )

        this.firestore.collection("players").add(newPlayer)
        this._playerMap.value[0] = newPlayer
    }

    fun deletePlayer(id: String) {
        this.firestore.collection("players").document(id).delete()
            .addOnSuccessListener { Log.d("debug", "deletePlayer: DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("debug", "deletePlayer: Error deleting document", e) }
    }

    private fun getPlayers() {
        this.firestore.collection("players")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.d("debug", "getPlayers: Error, $e")
                    return@addSnapshotListener
                }


                if (snapshots != null) {
                    val newMap: MutableMap<Int, Player> = mutableMapOf()

                    if(this._playerMap.value[0] != null) {
                        newMap[0] = this._playerMap.value[0]!!
                    }

                    for (snapshot in snapshots) {
                        val newPlayer = Player(
                            id = snapshot.id,
                            name = snapshot.data.getValue("name").toString()
                        )

                        if(newMap[0]?.id != newPlayer.id) {
                            newMap[newMap.size + 1] = newPlayer
                        }
                    }

                    this._playerMap.value = newMap
                }
            }
    }
}