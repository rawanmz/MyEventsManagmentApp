package com.example.myeventsmanagmentapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.myeventsmanagmentapp.data.entity.TagWithTaskLists
import com.example.myeventsmanagmentapp.data.entity.Tags
import com.example.myeventsmanagmentapp.data.entity.Task
import com.example.myeventsmanagmentapp.data.entity.TaskTagCrossRef
import com.example.myeventsmanagmentapp.data.entity.TaskWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Transaction
    @Upsert
    suspend fun addTask(task: Task): Long

    @Transaction
    @Upsert
    suspend fun insertTaskWithTags(task: Task, tags: List<Tags>)
    @Transaction
    @Upsert
    suspend fun insertTaskTagCrossRefs(taskTagCrossRefs: List<TaskTagCrossRef>)
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

    @Transaction
    @Query(" Select * From tags_table where tag_name = :tagName")
    fun getTagsWithTask(tagName: String): Flow<List<TagWithTaskLists>>

    @Query("SELECT * FROM task_table WHERE date LIKE :date")
    fun sortByCreationDate(date: String): Flow< List<TaskWithTags>>

    @Upsert
    suspend fun upsertTagList(tag: List<Tags>)

    @Transaction
    @Query("SELECT * FROM task_table")
    fun getTaskWithTags(): Flow<List<TaskWithTags>>

    @Transaction
    @Query("SELECT * FROM tags_table")
    fun getTagWithTaskLists(): Flow<List<TagWithTaskLists>>

}