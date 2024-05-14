package com.example.myeventsmanagmentapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myeventsmanagmentapp.data.dao.TaskDao
import com.example.myeventsmanagmentapp.data.entity.Tags
import com.example.myeventsmanagmentapp.data.entity.Task

@Database(entities = [Task::class, Tags::class], version = 1, exportSchema = false)
abstract class EventsDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: EventsDatabase? = null

        fun getDatabase(context: Context): EventsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database


//            val MIGRATION_1_2 = object : Migration(1, 2) {
//
//                override fun migrate(db: SupportSQLiteDatabase) {
//                    db.execSQL("ALTER TABLE tags_table ADD COLUMN tag_border TEXT NOT NULL DEFAULT ''")
//                }
//            }


            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java,
                    "events_database"
                )
                   //.addMigrations()
                   .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}