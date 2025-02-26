package com.noah.tictactoe.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@IgnoreExtraProperties
data class Player(
    val name: String = "",
)

class PlayerViewModel(private val preferenceManager: PreferenceManager?): ViewModel() {
    private val firestore = Firebase.firestore
    private val _playerMap: MutableStateFlow<MutableMap<String, Player>> = MutableStateFlow( mutableMapOf() )
    val playerMap: StateFlow<Map<String, Player>> = this._playerMap

    var localPlayerId: String? = null

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
                this.localPlayerId = it.id
            }
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
                    val newMap = snapshots.documents.associate { doc ->
                        doc.id to doc.toObject(Player::class.java)!!
                    } as MutableMap<String, Player>

                    this._playerMap.value = newMap
                }
            }
    }
}