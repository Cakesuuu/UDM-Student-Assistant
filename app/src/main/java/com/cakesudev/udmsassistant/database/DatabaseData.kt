package com.cakesudev.udmsassistant.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedulesTable")
data class DatabaseScheduleData(
    var dataType: String = "",

    var subjectName: String = "",
    var instructorName: String = "",
    var roomName: String = "",
    var startingTime: String = "",
    var endingTime: String = "",

    var isAlarmActivated: Boolean = false,

    var isMondaySelected: Boolean = false,
    var isTuesdaySelected: Boolean = false,
    var isWednesdaySelected: Boolean = false,
    var isThursdaySelected: Boolean = false,
    var isFridaySelected: Boolean = false,
    var isSaturdaySelected: Boolean = false,
    var isSundaySelected: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity(tableName = "deadlinesTable")
data class DatabaseDeadlineData(
    var dataType: String = "",

    var activityName: String = "",
    var activityDescription: String = "",
    var activityTicked: Boolean = false,
    var startingTime: String = "",
    var endingTime: String = "",
    var startingDate: String = "",
    var endingDate: String = "",

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity(tableName = "notesTable")
data class DatabaseNoteData(
    var dataType: String = "",

    var noteTitle: String = "",
    var noteBody: String = "",

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity(tableName = "adminTable")
data class DatabaseAdminData(
    var dataType: String = "",

    var adminName: String = "",
    var noticeTitle: String = "",
    var noticeImage: Int,
    var noticeBody: String = "",

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)