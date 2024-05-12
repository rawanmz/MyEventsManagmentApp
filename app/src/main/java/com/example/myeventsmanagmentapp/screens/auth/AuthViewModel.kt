package com.example.myeventsmanagmentapp.screens.auth

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myeventsmanagmentapp.navigation.Screens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val auth = Firebase.auth
    var isSignedIn =
        if (auth.currentUser == null) mutableStateOf(Screens.Authentication.route) else mutableStateOf(
            Screens.MainApp.route
        )
    val error = mutableStateOf("")


    fun login(email: String, password: String) {
        //error.value = ""
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Handle successful login
//                    error.value = "Successful "
                } else {
                    // Handle login failure
                    // Display error message to the user
                    error.value = task.exception?.message.orEmpty()
                }
            }
    }

    fun logout(context: Context) {
        auth.signOut()
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        ).signOut()
        isSignedIn.value = Screens.Authentication.route
    }


    fun signup(email: String, password: String) {
        //   error.value = ""
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Handle successful login
                    //error.value = "Successful "
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
                    //error.value = "Successful "
                } else {
                    // Handle login failure
                    // Display error message to the user
                    error.value = task.exception?.message.orEmpty()
                }
            }
    }
}
