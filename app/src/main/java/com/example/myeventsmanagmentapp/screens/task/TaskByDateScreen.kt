package com.example.myeventsmanagmentapp.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myeventsmanagmentapp.component.CalendarWeeklyView
import com.example.myeventsmanagmentapp.component.TaskCard

@Composable
fun TaskByDateScreen(viewmodel: TaskViewModel) {
    val tasks = viewmodel.tasks.collectAsState(initial = null)
    val tag = viewmodel.tags.collectAsState(null).value

    Column {
        CalendarWeeklyView(onDateSelected = {
            //filter tasks by selected date
        })
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasks.value.orEmpty()) { task ->

                TaskCard(
                    taskTitle = task.title,
                    task.timeFrom,
                    task.timeTo,
                    tag?.find { it.name == task.tagName }
                )
            }
        }
    }
}