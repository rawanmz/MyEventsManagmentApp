package com.example.myeventsmanagmentapp.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myeventsmanagmentapp.component.TaskCard
import com.example.myeventsmanagmentapp.component.TasksHeaderView
import com.example.myeventsmanagmentapp.data.entity.TagWithTaskLists

@Composable
fun TasksByCategory(tagWithTaskLists: TagWithTaskLists?, navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        TasksHeaderView(tagWithTaskLists?.tag?.name.orEmpty()) {
            navController.popBackStack()
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tagWithTaskLists?.tasks.orEmpty()) {

                TaskCard(
                    taskTitle = it.title,
                    it.timeFrom,
                    timeTo = it.timeTo,
                    tag = listOf(tagWithTaskLists?.tag)
                )
            }
        }
    }
}