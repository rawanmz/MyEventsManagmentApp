package com.example.myeventsmanagmentapp.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myeventsmanagmentapp.data.entity.Tags
import com.example.myeventsmanagmentapp.data.entity.Task
import com.example.myeventsmanagmentapp.data.entity.TaskType
import com.example.myeventsmanagmentapp.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val tasks = taskRepository.getAllTasks()
    val tags = taskRepository.getAllTags()
    val tasksbyTags = taskRepository.getTagsWithTask("Personal")
    init {
        viewModelScope.launch {
            taskRepository.insertTag(
                Tags(
                    "Work",
                    "color",
                    ""
                )
            )

            taskRepository.insertTag(
                Tags(
                    "Personal",
                    "color",
                    ""
                )
            )

            taskRepository.insertTask(
                Task(
                    title = "title",
                    description = "description",
                    date = "2022-02-02",
                    taskType = TaskType.OnGoing.type,
                    timeFrom = "10:20",
                    timeTo = "12:10",
                    tagName = "Work"
                )
            )
            taskRepository.insertTask(
                Task(
                    title = "title2",
                    description = "description",
                    date = "",
                    taskType = TaskType.OnGoing.type,
                    timeFrom = "10:202",
                    timeTo = "12:102",
                    tagName = "Personal"
                )
            )
        }

    }
}