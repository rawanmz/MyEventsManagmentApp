package com.example.myeventsmanagmentapp.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics


@Composable
fun HomeScreen() {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Magenta)
        .semantics {
            contentDescription = "Home Screen"
        }) {

    }
}