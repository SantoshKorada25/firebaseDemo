package com.visiontek.firebasedemo.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

data class Note(
    val id:String="",
    val content:String="",
    val timestamp:Long=0L
)

class NoteViewModel : ViewModel () {
    private val notes = mutableListOf<Note>()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    // Function to save a note
    fun addNote(text: String) {
        val userId = auth.currentUser?.uid ?: return
        val noteData = hashMapOf(
            "content" to text,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users").document(userId)
            .collection("notes")
            .add(noteData) // Firestore automatically generates a unique ID for the note
    }

    // Function to read notes in real-time
    fun getNotes(onResult: (List<Note>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .collection("notes")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                val notes = snapshot?.documents?.map { doc ->
                    Note(
                        id = doc.id,
                        content = doc.getString("content") ?: "",
                        timestamp = doc.getLong("timestamp") ?: 0L
                    )
                } ?: emptyList()
                onResult(notes)
            }
    }
}