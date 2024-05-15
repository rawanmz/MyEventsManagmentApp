package com.example.myeventsmanagmentapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.myeventsmanagmentapp.data.entity.Tags
import com.example.myeventsmanagmentapp.data.entity.Task
import com.example.myeventsmanagmentapp.data.entity.TaskWithTagLists
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun addTask(task: Task)
    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * From task_table")
    fun getAllTasks(): Flow<List<Task>>

    @Upsert//@Insert(onConflict = OnConflictStrategy.Replace)
    suspend fun upsertTag(tag: Tags)
    @Delete
    suspend fun deleteTag(tag: Tags)
    @Query("SELECT * From tags_table")
    fun getAllTags(): Flow<List<Tags>>

    @Query(" Select * From tags_table where tag_name = :tagName")
    fun getTagsWithTask(tagName: String): Flow<List<TaskWithTagLists>>

    @Query("SELECT * FROM task_table WHERE date LIKE :date")
     fun sortByCreationDate(date: String): Flow<List<Task>>

    @Upsert
     suspend fun upsertTagList(tag: List<Tags>)
}