package com.example.myeventsmanagmentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myeventsmanagmentapp.navigation.EventsAppNavigation
import com.example.myeventsmanagmentapp.screens.auth.AuthViewModel
import com.example.myeventsmanagmentapp.ui.theme.MyEventsManagmentAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.FirebaseApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val authViewModel: AuthViewModel = hiltViewModel()
            MyEventsManagmentAppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (authViewModel.error.value.isNotEmpty()) {
                        Snackbar(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            containerColor = Color.Red.copy(0.5f)
                        ) {
                            Text(
//                                modifier = Modifier.matchParentSize(),
                                text = authViewModel.error.value
                            )
                        }
                    }
                    EventsAppNavigation(authViewModel)
                }
            }
        }
    }
}
