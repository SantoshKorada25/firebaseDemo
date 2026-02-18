package com.visiontek.firebasedemo.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Note(
    val id:String="",
    val content:String="",
    val timestamp:Long=0L
)

class NoteViewModel : ViewModel () {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    init {
        // 2. Start listening for notes as soon as the ViewModel is created
        fetchNotes()
    }

    // 3. Create a private function to fetch notes and update the StateFlow
    private fun fetchNotes() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId)
            .collection("notes")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Handle the error, e.g., log it or show a message
                    return@addSnapshotListener
                }
                // Map the Firestore documents to your Note data class
                val notesList = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Note::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                // 4. Update the value of the StateFlow. The UI will automatically react.
                _notes.value = notesList
            }
    }


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
            .addOnSuccessListener {
                println("Note Successfully added !")
            }
            .addOnFailureListener {
              e->  print("Error adding note : ${e.message}")
            }
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

    // 5. Add a function to delete a note
    fun deleteNote(noteId: String) {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId)
            .collection("notes")
            .document(noteId)
            .delete()
    }
}