package com.cakesudev.udmsassistant.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

//Schedule DAO
@Dao
interface DatabaseScheduleDao {

    @Upsert
    suspend fun insertOrUpdateNewData(data: DatabaseScheduleData)

    @Update
    suspend fun updateData(data: DatabaseScheduleData)

    @Delete
    suspend fun deleteOldData(data: DatabaseScheduleData)

    @Query("SELECT COUNT(*) FROM schedulesTable")
    suspend fun getCount(): Int

    @Query("SELECT * FROM schedulesTable WHERE id = :id")
    suspend fun getDataById(id: Int): DatabaseScheduleData

    @Query("SELECT * from schedulesTable ORDER BY id ASC")
    fun orderByDateAdded() : Flow<List<DatabaseScheduleData>>

    @Query("SELECT * from schedulesTable ORDER BY startingTime DESC")
    fun orderByNextSchedule() : Flow<List<DatabaseScheduleData>>

    @Query("SELECT * from schedulesTable ORDER BY subjectName ASC")
    fun orderBySubjectName() : Flow<List<DatabaseScheduleData>>

    @Query("SELECT COUNT(*) FROM schedulesTable")
    fun getRowCount(): Int

}

//Deadline DAO
@Dao
interface DatabaseDeadlineDao {

    @Upsert
    suspend fun insertOrUpdateNewData(data: DatabaseDeadlineData)

    @Update
    suspend fun updateData(data: DatabaseDeadlineData)

    @Delete
    suspend fun deleteOldData(data: DatabaseDeadlineData)

    @Query("SELECT COUNT(*) FROM deadlinesTable")
    suspend fun getCount(): Int

    @Query("SELECT * FROM deadlinesTable WHERE id = :id")
    suspend fun getDataById(id: Int): DatabaseDeadlineData

    @Query("SELECT * from deadlinesTable ORDER BY id ASC")
    fun orderByDateAdded() : Flow<List<DatabaseDeadlineData>>

    @Query("SELECT * from deadlinesTable ORDER BY startingDate DESC")
    fun orderByNextActivity() : Flow<List<DatabaseDeadlineData>>

    @Query("SELECT * from deadlinesTable ORDER BY startingTime DESC")
    fun orderByNextActivityTime() : Flow<List<DatabaseDeadlineData>>

    @Query("SELECT * from deadlinesTable ORDER BY activityName ASC")
    fun orderByActivityName() : Flow<List<DatabaseDeadlineData>>

    @Query("SELECT COUNT(*) FROM deadlinesTable")
    fun getRowCount(): Int

}

//Note DAO
@Dao
interface DatabaseNoteDao {

    @Upsert
    suspend fun insertOrUpdateNewData(data: DatabaseNoteData)

    @Delete
    suspend fun deleteOldData(data: DatabaseNoteData)

    @Query("SELECT COUNT(*) FROM notesTable")
    suspend fun getCount(): Int

    @Query("SELECT * from notesTable ORDER BY id ASC")
    fun orderByDateAdded() : Flow<List<DatabaseNoteData>>

    @Query("SELECT * from notesTable ORDER BY noteTitle ASC")
    fun orderByNoteTitle() : Flow<List<DatabaseNoteData>>

}

//Admin DAO
@Dao
interface DatabaseAdminDao {

    @Upsert
    suspend fun insertOrUpdateNewData(data: DatabaseAdminData)

    @Delete
    suspend fun deleteOldData(data: DatabaseAdminData)

    @Query("SELECT COUNT(*) FROM adminTable")
    suspend fun getCount(): Int

    @Query("SELECT * from adminTable ORDER BY id ASC")
    fun orderByDateAdded() : Flow<List<DatabaseAdminData>>

    @Query("SELECT * from adminTable ORDER BY noticeTitle ASC")
    fun orderByNoticeTitle() : Flow<List<DatabaseAdminData>>

    @Query("SELECT * FROM adminTable ORDER BY adminName ASC")
    fun orderByAdminName() : Flow<List<DatabaseAdminData>>

}