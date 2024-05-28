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
import com.example.myeventsmanagmentapp.data.entity.TaskTagCrossRef

@Database(entities = [Task::class, Tags::class, TaskTagCrossRef::class], version = 4, exportSchema = false)
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


            val MIGRATION_1_2 = object : Migration(1, 2) {

                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE tags_table ADD COLUMN icon_name TEXT NOT NULL DEFAULT ''")
                }
            }

            val MIGRATION_2_3 = object : Migration(2, 3) {

                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        "CREATE TABLE IF NOT EXISTS `TaskTagCrossRef` (" +
                                "`task_Id` INTEGER NOT NULL, " +
                                "`tag_name` TEXT NOT NULL, " +
                                "PRIMARY KEY(`task_Id`, `tag_name`) )"
                    )
                }
            }
            val MIGRATION_3_4 = object : Migration(3, 4) {

                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE tags_table ADD COLUMN isSelected INTEGER DEFAULT 0 NOT NULL")

                }
            }
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java,
                    "events_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3,MIGRATION_3_4)
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}