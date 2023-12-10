package com.cakesudev.udmsassistant.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DatabaseScheduleData::class,
        DatabaseNoteData::class,
        DatabaseDeadlineData::class,
        DatabaseAdminData::class ],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {

    abstract val ScheduleDatabaseDao: DatabaseScheduleDao
    abstract val DeadlineDatabaseDao: DatabaseDeadlineDao
    abstract val NoteDatabaseDao: DatabaseNoteDao
    abstract val AdminDatabaseDao: DatabaseAdminDao

}