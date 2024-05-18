package com.example.myeventsmanagmentapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("tags_table")
data class Tags(
    @PrimaryKey
    @ColumnInfo(name = "tag_name")
    val name: String,
    @ColumnInfo(name = "tag_color")
    val color: String,
    @ColumnInfo(name = "icon_name")
    val iconName: String,
    @ColumnInfo(name = "task_in_tag_id")
    val taskId: String? = null,
)


data class TaskWithTagLists(
    @Embedded val tag: Tags,
    @Relation(
        parentColumn = "tag_name",
        entityColumn = "task_tag_name"
    )
    var tasks: List<Task>
)

data class TagWithTasksLists(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "task_id",
        entityColumn = "task_in_tag_id"
    )
    var tags: List<Tags>
)