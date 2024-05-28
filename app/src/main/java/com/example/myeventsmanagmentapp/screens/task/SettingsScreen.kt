package com.example.myeventsmanagmentapp.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.myeventsmanagmentapp.LocaleUtils
import com.example.myeventsmanagmentapp.component.TasksHeaderView

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column {
        TasksHeaderView("Settings") {
            navController.popBackStack()
        }
        val checkedState = remember { mutableStateOf("en") }
        
        LocaleUtils.SetLanguage(locale = checkedState.value)
        Switch(
            checked = checkedState.value == "en", onCheckedChange = {
                if (it)
                    checkedState.value = "en"
                else
                    checkedState.value = "ar"
                navController.popBackStack()

            },
            thumbContent = {
                Text(checkedState.value)
            }
        )
    }
}