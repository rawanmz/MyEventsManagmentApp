package com.example.myeventsmanagmentapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_Id")
    var taskId: Long? = null,
    @ColumnInfo(name = "task_title")
    val title: String,
    @ColumnInfo(name = "task_description")
    val description: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "time_from")
    val timeFrom: String? = "",
    @ColumnInfo(name = "time_to")
    val timeTo: String? = "",
    @ColumnInfo(name = "task_type")
    val taskType: String,
    @ColumnInfo(name = "task_tag_name")
    val tagName: String = ""
)

enum class TaskType(val type: String) {
    Pending("Pending"),
    OnGoing("On Going"),
    Cancelled("Cancelled"),
    Completed("Completed")
}
