package com.noah.tictactoe.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.noah.tictactoe.data.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayerViewModel(private val preferenceManager: PreferenceManager?): ViewModel() {
    private val firestore = Firebase.firestore
    private val _playerMap: MutableStateFlow<MutableMap<String, Player>> = MutableStateFlow( mutableMapOf() )
    val playerMap: StateFlow<Map<String, Player>> = this._playerMap

    init {
        this.getPlayers()
    }

    fun getLocalPlayerID(): String? {
       return preferenceManager?.getString("localPlayerID")
    }

    fun addPlayer(name: String) {
        val newPlayer = Player(
            name = name
        )

        this.firestore.collection("players").add(newPlayer)
            .addOnSuccessListener {
                preferenceManager?.saveString("localPlayerID", it.id)
            }
    }

    private fun getPlayers() {
        this.firestore.collection("players")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.d("debug", "getPlayers: Error, $e")
                    return@addSnapshotListener
                }


                if (snapshots != null) {
                    val newMap = snapshots.documents.associate { doc ->
                        doc.id to doc.toObject(Player::class.java)!!
                    } as MutableMap<String, Player>

                    this._playerMap.value = newMap
                }
            }
    }
}