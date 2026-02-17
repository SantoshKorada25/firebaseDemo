package com.visiontek.firebasedemo.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val auth:FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState: StateFlow<Boolean?> = _loginState

    fun login(email:String, password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                _loginState.value = it.isSuccessful
            }
    }
    fun signUp(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                _loginState.value = it.isSuccessful
            }

    }
    fun logout(){
        auth.signOut()
    }
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }


}