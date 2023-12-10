package com.cakesudev.udmsassistant.database

//Schedule Event
sealed interface DatabaseScheduleEvent {

    object SaveData: DatabaseScheduleEvent

    data class SetDataType(val dataType: String): DatabaseScheduleEvent

    data class SetSubjectName(val subjectName: String): DatabaseScheduleEvent
    data class SetInstructorName(val instructorName: String): DatabaseScheduleEvent
    data class SetRoomName(val roomName: String): DatabaseScheduleEvent
    data class SetStartingTime(val startingTime: String): DatabaseScheduleEvent
    data class SetEndingTime(val endingTime: String): DatabaseScheduleEvent

    data class SetAlarmActivated(val alarmStatus: Boolean): DatabaseScheduleEvent

    data class UpdateAlarmActivated(
        val dataId: Int,
        val updateAlarmActivated: Boolean,
    ) : DatabaseScheduleEvent

    data class SetMondayToggle(val monToggle: Boolean): DatabaseScheduleEvent
    data class SetTuesdayToggle(val tueToggle: Boolean): DatabaseScheduleEvent
    data class SetWednesdayToggle(val wedToggle: Boolean): DatabaseScheduleEvent
    data class SetThursdayToggle(val thuToggle: Boolean): DatabaseScheduleEvent
    data class SetFridayToggle(val friToggle: Boolean): DatabaseScheduleEvent
    data class SetSaturdayToggle(val satToggle: Boolean): DatabaseScheduleEvent
    data class SetSundayToggle(val sunToggle: Boolean): DatabaseScheduleEvent

    data class SortingSystem(val sortType: DatabaseScheduleSortType): DatabaseScheduleEvent

    data class DeleteSchedule(val data: DatabaseScheduleData): DatabaseScheduleEvent
}

//Deadline Event
sealed interface DatabaseDeadlineEvent {

    object SaveData: DatabaseDeadlineEvent

    data class SetDataType(val dataType: String): DatabaseDeadlineEvent

    data class SetActivityName(val activityName: String): DatabaseDeadlineEvent
    data class SetActivityDescription(val activityDescription: String): DatabaseDeadlineEvent
    data class SetActivityTicked(val activityTicked: Boolean): DatabaseDeadlineEvent
    data class SetActivityStartingTime(val activityStartingTime: String): DatabaseDeadlineEvent
    data class SetActivityEndingTime(val activityEndingTime: String): DatabaseDeadlineEvent
    data class SetActivityStartingDate(val activityStartingDate: String): DatabaseDeadlineEvent
    data class SetActivityEndingDate(val activityEndingDate: String): DatabaseDeadlineEvent

    data class SortingSystem(val sortType: DatabaseDeadlineSortType): DatabaseDeadlineEvent

    data class DeleteDeadline(val data: DatabaseDeadlineData): DatabaseDeadlineEvent

    data class UpdateActivityTicked(
        val dataId: Int,
        val UpdateActivityTicked: Boolean,
    ) : DatabaseDeadlineEvent

}

//Note Event
sealed interface DatabaseNoteEvent {

    object SaveData: DatabaseNoteEvent

    data class SetDataType(val dataType: String): DatabaseNoteEvent

    data class SetNoteTitle(val noteTitle: String): DatabaseNoteEvent
    data class SetNoteBody(val noteBody: String): DatabaseNoteEvent

    data class SortingSystem(val sortType: DatabaseNoteSortType): DatabaseNoteEvent

    data class DeleteNote(val data: DatabaseNoteData): DatabaseNoteEvent
}

//Admin Event
sealed interface DatabaseAdminEvent {

    object SaveData: DatabaseAdminEvent

    data class SetDataType(val dataType: String): DatabaseAdminEvent

    data class SetNoticeTitle(val noticeTitle: String): DatabaseAdminEvent
    data class SetAdminName(val adminName: String): DatabaseAdminEvent
    data class SetNoticeImage(val noticeImage: Int): DatabaseAdminEvent
    data class SetNoticeBody(val noticeBody: String): DatabaseAdminEvent

    data class SortingSystem(val sortType: DatabaseAdminSortType): DatabaseAdminEvent

    data class DeleteNotice(val data: DatabaseAdminData): DatabaseAdminEvent
}