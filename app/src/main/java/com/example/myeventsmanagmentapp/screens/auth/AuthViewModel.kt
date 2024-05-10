package com.example.myeventsmanagmentapp.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myeventsmanagmentapp.navigation.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    var isSignedIn =
        if (Firebase.auth.currentUser == null) mutableStateOf(Screens.Authentication.route) else mutableStateOf(
            Screens.MainApp.route
        )
    private val auth = Firebase.auth
    val error = mutableStateOf("")
    fun login(email: String, password: String) {
        //error.value = ""
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Handle successful login
                    error.value = "Successful "
                } else {
                    // Handle login failure
                    val exception = task.exception
                    // Display error message to the user
                    error.value = task.exception?.message.orEmpty()
                }
            }
    }

    fun signup(email: String, password: String) {
        //   error.value = ""
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Handle successful login
                    error.value = "Successful "
                } else {
                    // Handle login failure
                    val exception = task.exception
                    // Display error message to the user
                    error.value = exception?.message.orEmpty()
                }
            }
    }

    fun restPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Handle successful login
                    error.value = "Successful "
                } else {
                    // Handle login failure
                    val exception = task.exception
                    // Display error message to the user
                    error.value = task.exception?.message.orEmpty()
                }
            }
    }
}