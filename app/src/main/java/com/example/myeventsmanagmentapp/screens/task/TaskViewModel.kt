package com.example.myeventsmanagmentapp.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myeventsmanagmentapp.data.entity.Tags
import com.example.myeventsmanagmentapp.data.entity.Task
import com.example.myeventsmanagmentapp.data.entity.TaskType
import com.example.myeventsmanagmentapp.data.entity.TaskWithTagLists
import com.example.myeventsmanagmentapp.data.repository.TaskRepository
import com.example.myeventsmanagmentapp.getIconName
import com.example.myeventsmanagmentapp.iconByName
import com.example.myeventsmanagmentapp.ui.theme.LightBlue
import com.example.myeventsmanagmentapp.ui.theme.LightGreen
import com.example.myeventsmanagmentapp.ui.theme.LightOrange
import com.example.myeventsmanagmentapp.ui.theme.LightPurple
import com.example.myeventsmanagmentapp.ui.theme.LightRed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val tasks = mutableStateOf<List<Task>>(emptyList())

    val tags = taskRepository.getAllTags()
    val cancelledTasks = taskRepository.getTagsWithTask(TaskType.Cancelled.type)
    val pendingTasks = taskRepository.getTagsWithTask(TaskType.Pending.type)
    val completedTasks = taskRepository.getTagsWithTask(TaskType.Completed.type)
    val onGoingTasks = taskRepository.getTagsWithTask(TaskType.OnGoing.type)

    init {
        //add base tags
        viewModelScope.launch {
            val tagsList = TaskType.entries.map {
                Tags(it.type, it.color, it.icon)
            }
            taskRepository.insertTagList(tagsList)
        }

    }

    fun sortTasksByDate(date: String) {
        viewModelScope.launch {
            taskRepository.sortTasksByDate(date).collect {
                tasks.value = it
            }
        }
    }
}