package com.visiontek.firebasedemo.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.userProfileChangeRequest

class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val storage =

    // UI States
    var isUploading by mutableStateOf(false)
        private set

    var uploadError by mutableStateOf<String?>(null)
        private set

    /**
     * Uploads the picked image to Firebase Storage and updates
     * the user's Firebase Profile photoUrl.
     */
    fun uploadProfilePicture(uri: Uri, onSuccess: (Uri) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        val storageRef = storage.reference.child("profile_pictures/$userId.jpg")

        isUploading = true
        uploadError = null

        storageRef.putFile(uri)
            .addOnSuccessListener {
                // 1. Get the Download URL from Storage
                storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    // 2. Update the Firebase Auth Profile with this URL
                    val profileUpdates = userProfileChangeRequest {
                        UserProfileChangeRequest.Builder.setPhotoUri = downloadUrl
                    }

                    auth.currentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            isUploading = false
                            if (task.isSuccessful) {
                                onSuccess(downloadUrl)
                            } else {
                                uploadError = "Profile update failed"
                            }
                        }
                }
            }
            .addOnFailureListener {
                isUploading = false
                uploadError = it.message ?: "Upload failed"
            }
    }

    fun clearError() {
        uploadError = null
    }
}