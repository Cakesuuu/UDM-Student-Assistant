package com.cakesudev.udmsassistant.database

data class DatabaseScheduleState(
    val data: List<DatabaseScheduleData> = emptyList(),

    val dataType: String = "",

    val subjectName: String = "",
    val instructorName: String = "",
    val roomName: String = "",
    val startingTime: String = "",
    val endingTime: String = "",

    val isAlarmActivated: Boolean = false,

    val isMondaySelected: Boolean = false,
    val isTuesdaySelected: Boolean = false,
    val isWednesdaySelected: Boolean = false,
    val isThursdaySelected: Boolean = false,
    val isFridaySelected: Boolean = false,
    val isSaturdaySelected: Boolean = false,
    val isSundaySelected: Boolean = false,

    val sortType: DatabaseScheduleSortType = DatabaseScheduleSortType.BY_STARTING_TIME
)

data class DatabaseDeadlineState(
    val data: List<DatabaseDeadlineData> = emptyList(),

    val dataType: String = "",

    val activityName: String = "",
    val activityDescription: String = "",
    val activityTicked: Boolean = false,
    val startingTime: String = "",
    val endingTime: String = "",
    val startingDate: String = "",
    val endingDate: String = "",

    val sortType: DatabaseDeadlineSortType = DatabaseDeadlineSortType.BY_STARTING_TIME
)

data class DatabaseNoteState(
    val data: List<DatabaseNoteData> = emptyList(),

    val dataType: String = "",

    val noteTitle: String = "",
    val noteBody: String = "",

    val sortType: DatabaseNoteSortType = DatabaseNoteSortType.CHRONOLOGICAL
)

data class DatabaseAdminState(
    val data: List<DatabaseAdminData> = emptyList(),

    val dataType: String = "",

    val adminName: String = "",
    val noticeTitle: String = "",
    val noticeImage: Int = 0,
    val noticeBody: String = "",

    val sortType: DatabaseAdminSortType = DatabaseAdminSortType.CHRONOLOGICAL
)