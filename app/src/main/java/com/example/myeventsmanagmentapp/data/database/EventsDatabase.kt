package com.example.myeventsmanagmentapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [Task::class], version = 1, exportSchema = false)
//abstract class EventsDatabase : RoomDatabase() {
//
//    abstract fun taskDao(): TaskDao
//
//    companion object {
//        // Singleton prevents multiple instances of database opening at the
//        // same time.
//        @Volatile
//        private var INSTANCE: EventsDatabase? = null
//
//        fun getDatabase(context: Context): EventsDatabase {
//            // if the INSTANCE is not null, then return it,
//            // if it is, then create the database
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    EventsDatabase::class.java,
//                    "events_database"
//                )
//                    .fallbackToDestructiveMigration().build()
//                INSTANCE = instance
//                // return instance
//                instance
//            }
//        }
//    }
//}