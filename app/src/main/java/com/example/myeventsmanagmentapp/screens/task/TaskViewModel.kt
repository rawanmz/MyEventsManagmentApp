package com.example.myeventsmanagmentapp.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myeventsmanagmentapp.data.entity.Tags
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
                    "color"
                )
            )

            taskRepository.insertTag(
                Tags(
                    "Personal",
                    "color"
                )
            )
        }

    }
}