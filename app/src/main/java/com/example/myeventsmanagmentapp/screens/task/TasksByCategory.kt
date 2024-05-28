package com.example.myeventsmanagmentapp.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myeventsmanagmentapp.component.SearchComponent
import com.example.myeventsmanagmentapp.component.TaskCard
import com.example.myeventsmanagmentapp.component.TasksHeaderView
import com.example.myeventsmanagmentapp.navigation.Screens

@Composable
fun TasksByCategory(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    tag: String?
) {
    LaunchedEffect(Unit) {
        taskViewModel.getTasksByTagName(tag.orEmpty())
    }
    val results = taskViewModel.taskWithTags.value
    Column(modifier = Modifier.padding(16.dp)) {

        TasksHeaderView(tag.orEmpty()) {
            navController.popBackStack()
        }
        SearchComponent {
            taskViewModel.searchInTasksAndTags(it)
        }

        Spacer(modifier = Modifier.size(18.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(results) {

                TaskCard(
                    taskTitle = it.task.title.orEmpty(),
                    it.task.timeFrom,
                    timeTo = it.task.timeTo,
                    tag = it.tags.filter { it.name == tag.orEmpty() },
                    onDelete = {
                    },
                    onClick = {
                        navController.navigate("${Screens.MainApp.UpdateTask.route}/${it?.task?.taskId}")
                    })

            }
        }
    }
}