package com.cakesudev.udmsassistant.database

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cakesudev.udmsassistant.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DatabaseScheduleViewModel(
    private val dao: DatabaseScheduleDao
): ViewModel() {

    init {
        Log.d("DatabaseNoteViewModel", "ViewModel instance created: ${this.hashCode()}")
    }

    private val _sortType = MutableStateFlow(DatabaseScheduleSortType.CHRONOLOGICAL)
    private val _schedules = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                DatabaseScheduleSortType.CHRONOLOGICAL -> dao.orderByDateAdded()
                DatabaseScheduleSortType.BY_STARTING_TIME -> dao.orderByNextSchedule()
                DatabaseScheduleSortType.BY_SUBJECT_NAME -> dao.orderBySubjectName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(DatabaseScheduleState())
    val state = combine(_state, _sortType, _schedules) { state, sortType, schedules ->
        state.copy(
            data = schedules,
            sortType = sortType
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DatabaseScheduleState())

    init {
        viewModelScope.launch {
            if (dao.getCount() == 0) {
                prepopulateDatabase()
            }
        }
    }

    private suspend fun prepopulateDatabase() {
        dao.insertOrUpdateNewData(
            DatabaseScheduleData(
                dataType = "Schedule",

                subjectName = "You don't have schedules yet.",
                instructorName = "Add one to get started!",
                roomName = "Thank you for using our app!",
                startingTime = "12:00 PM",
                endingTime = "12:00 PM",
        
                isAlarmActivated = false,
        
                isMondaySelected = true,
                isTuesdaySelected = true,
                isWednesdaySelected = true,
                isThursdaySelected = true,
                isFridaySelected = true,
                isSaturdaySelected = true,
                isSundaySelected = true,
                
            )
        )
    }

    fun onUpdateSchedule(data: DatabaseScheduleData) {
        viewModelScope.launch {
            dao.updateData(data)
        }
    }

    fun onEvent(event: DatabaseScheduleEvent) {
        when(event) {
            is DatabaseScheduleEvent.DeleteSchedule -> {
                viewModelScope.launch {
                    dao.deleteOldData(event.data)
                }
            }
            DatabaseScheduleEvent.SaveData -> {
                val dataType = state.value.dataType
                val subjectName = state.value.subjectName
                val instructorName = state.value.instructorName
                val roomName = state.value.roomName
                val startingTime = state.value.startingTime
                val endingTime = state.value.endingTime

                val isAlarmActivated = state.value.isAlarmActivated

                val isMondaySelected = state.value.isMondaySelected
                val isTuesdaySelected = state.value.isTuesdaySelected
                val isWednesdaySelected = state.value.isWednesdaySelected
                val isThursdaySelected = state.value.isThursdaySelected
                val isFridaySelected = state.value.isFridaySelected
                val isSaturdaySelected = state.value.isSaturdaySelected
                val isSundaySelected = state.value.isSundaySelected

                val data = DatabaseScheduleData(
                    dataType = dataType,
                    subjectName = subjectName,
                    instructorName = instructorName,
                    roomName = roomName,
                    startingTime = startingTime,
                    endingTime = endingTime,

                    isAlarmActivated = isAlarmActivated,

                    isMondaySelected = isMondaySelected,
                    isTuesdaySelected = isTuesdaySelected,
                    isWednesdaySelected = isWednesdaySelected,
                    isThursdaySelected = isThursdaySelected,
                    isFridaySelected = isFridaySelected,
                    isSaturdaySelected = isSaturdaySelected,
                    isSundaySelected = isSundaySelected,

                    )
                viewModelScope.launch {
                    dao.insertOrUpdateNewData(data)
                }
                _state.update { it.copy(

                    dataType = "",

                    subjectName = "",
                    instructorName = "",
                    roomName = "",
                    startingTime = "",
                    endingTime = "",

                    isAlarmActivated = false,

                    isMondaySelected = false,
                    isTuesdaySelected = false,
                    isWednesdaySelected = false,
                    isThursdaySelected = false,
                    isFridaySelected = false,
                    isSaturdaySelected = false,
                    isSundaySelected = false
                )
                }

            }

            //Basic Information
            is DatabaseScheduleEvent.SetDataType -> {
                _state.update { it.copy(
                    dataType = event.dataType
                ) }
            }

            is DatabaseScheduleEvent.SetSubjectName -> {
                _state.update { it.copy(
                    subjectName = event.subjectName
                ) }
            }

            is DatabaseScheduleEvent.SetInstructorName -> {
                _state.update { it.copy(
                    instructorName = event.instructorName
                ) }
            }

            is DatabaseScheduleEvent.SetRoomName -> {
                _state.update { it.copy(
                    roomName = event.roomName
                ) }
            }

            is DatabaseScheduleEvent.SetAlarmActivated -> {
                _state.update { it.copy(
                    isAlarmActivated = event.alarmStatus
                ) }
            }

            is DatabaseScheduleEvent.UpdateAlarmActivated -> {
                viewModelScope.launch {
                    val existingData = dao.getDataById(event.dataId)
                    val updatedData = existingData.copy(
                        isAlarmActivated = event.updateAlarmActivated
                    )
                    onUpdateSchedule(updatedData)
                }
            }

            is DatabaseScheduleEvent.SetStartingTime -> {
                _state.update { it.copy(
                    startingTime = event.startingTime
                ) }
            }

            is DatabaseScheduleEvent.SetEndingTime -> {
                _state.update { it.copy(
                    endingTime = event.endingTime
                ) }
            }

            //Day Toggles
            is DatabaseScheduleEvent.SetMondayToggle -> {
                _state.update { it.copy(
                    isMondaySelected = true
                ) }
            }

            is DatabaseScheduleEvent.SetTuesdayToggle -> {
                _state.update { it.copy(
                    isTuesdaySelected = true
                ) }
            }

            is DatabaseScheduleEvent.SetWednesdayToggle -> {
                _state.update { it.copy(
                    isWednesdaySelected = true
                ) }
            }

            is DatabaseScheduleEvent.SetThursdayToggle -> {
                _state.update { it.copy(
                    isThursdaySelected = true
                ) }
            }

            is DatabaseScheduleEvent.SetFridayToggle -> {
                _state.update { it.copy(
                    isFridaySelected = true
                ) }
            }

            is DatabaseScheduleEvent.SetSaturdayToggle -> {
                _state.update { it.copy(
                    isSaturdaySelected = true
                ) }
            }

            is DatabaseScheduleEvent.SetSundayToggle -> {
                _state.update { it.copy(
                    isSundaySelected = true
                ) }
            }

            //Sorting System
            is DatabaseScheduleEvent.SortingSystem -> {
                _sortType.value = event.sortType
            }

        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class DatabaseNoteViewModel(
    private val dao: DatabaseNoteDao
): ViewModel() {

    init {
        Log.d("DatabaseNoteViewModel", "ViewModel instance created: ${this.hashCode()}")
    }

    private val _sortType = MutableStateFlow(DatabaseNoteSortType.CHRONOLOGICAL)
    private val _notes = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                DatabaseNoteSortType.CHRONOLOGICAL -> dao.orderByDateAdded()
                DatabaseNoteSortType.BY_TITLE -> dao.orderByNoteTitle()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(DatabaseNoteState())
    val state = combine(_state, _sortType, _notes) { state, sortType, notes ->
        state.copy(
            data = notes,
            sortType = sortType
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DatabaseNoteState())

    init {
        viewModelScope.launch {
            if (dao.getCount() == 0) {
                prepopulateDatabase()
            }
        }
    }

    private suspend fun prepopulateDatabase() {
        dao.insertOrUpdateNewData(
            DatabaseNoteData(
                dataType = "Note",
                noteTitle = "Oh no!",
                noteBody = "You don't have notes yet! Better make one now and try out our main feature!"
                )
        )
    }

    fun onEvent(event: DatabaseNoteEvent) {
        when(event) {
            is DatabaseNoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteOldData(event.data)
                }
            }
            DatabaseNoteEvent.SaveData -> {
                val dataType = state.value.dataType
                val noteTitle = state.value.noteTitle
                val noteBody = state.value.noteBody

                if (noteBody.isBlank() or noteTitle.isBlank()){
                    return
                }

                val data = DatabaseNoteData(
                    dataType = dataType,
                    noteTitle = noteTitle,
                    noteBody = noteBody

                )
                viewModelScope.launch {
                    dao.insertOrUpdateNewData(data)
                }
                _state.update {
                    it.copy(
                        dataType = "",
                        noteTitle = "",
                        noteBody = ""
                    )
                }

            }

            //Basic Information
            is DatabaseNoteEvent.SetDataType -> {
                _state.update { it.copy(
                    dataType = event.dataType
                ) }
            }

            is DatabaseNoteEvent.SetNoteTitle -> {
                _state.update { it.copy(
                    noteTitle = event.noteTitle
                ) }
            }

            is DatabaseNoteEvent.SetNoteBody -> {
                _state.update { it.copy(
                    noteBody = event.noteBody
                ) }
            }

            //Sorting System
            is DatabaseNoteEvent.SortingSystem -> {
                _sortType.value = event.sortType
            }

        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class DatabaseDeadlineViewModel(
    private val dao: DatabaseDeadlineDao
): ViewModel() {

    init {
        Log.d("DatabaseDeadlineViewModel", "ViewModel instance created: ${this.hashCode()}")
    }

    private val _sortType = MutableStateFlow(DatabaseDeadlineSortType.BY_STARTING_DATE)
    private val _deadlines = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                DatabaseDeadlineSortType.CHRONOLOGICAL -> dao.orderByDateAdded()
                DatabaseDeadlineSortType.BY_STARTING_DATE -> dao.orderByNextActivity()
                DatabaseDeadlineSortType.BY_ACTIVITY_NAME -> dao.orderByActivityName()
                DatabaseDeadlineSortType.BY_STARTING_TIME -> dao.orderByNextActivityTime()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(DatabaseDeadlineState())
    val state = combine(_state, _sortType, _deadlines) { state, sortType, deadlines ->
        state.copy(
            data = deadlines,
            sortType = sortType
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DatabaseDeadlineState())

    init {
        viewModelScope.launch {
            if (dao.getCount() == 0) {
                prepopulateDatabase()
            }
        }
    }

    private suspend fun prepopulateDatabase() {
        dao.insertOrUpdateNewData(
            DatabaseDeadlineData(
                dataType = "Deadline",

                activityName = "You have no deadlines active!",
                activityDescription = "To set up a new deadline, press the middle button below!",
                activityTicked = false,

                startingTime = "",
                endingTime = "",

                startingDate = "Now!",
                endingDate = "",
            )
        )
    }

    fun onUpdateDeadline(data: DatabaseDeadlineData) {
        viewModelScope.launch {
            dao.updateData(data)
        }
    }

    fun onEvent(event: DatabaseDeadlineEvent) {
        when(event) {
            is DatabaseDeadlineEvent.DeleteDeadline -> {
                viewModelScope.launch {
                    dao.deleteOldData(event.data)
                }
            }
            DatabaseDeadlineEvent.SaveData -> {
                val dataType = state.value.dataType

                val activityName = state.value.activityName
                val activityDescription = state.value.activityDescription
                val activityTicked = state.value.activityTicked

                val startingTime = state.value.startingTime
                val endingTime = state.value.endingTime

                val startingDate = state.value.startingDate
                val endingDate = state.value.endingDate

                val data = DatabaseDeadlineData(
                    dataType = dataType,

                    activityName = activityName,
                    activityDescription = activityDescription,
                    activityTicked = activityTicked,

                    startingTime = startingTime,
                    endingTime = endingTime,

                    startingDate = startingDate,
                    endingDate = endingDate
                    )
                viewModelScope.launch {
                    dao.insertOrUpdateNewData(data)
                }
                _state.update {
                    it.copy(

                        dataType = "",

                        activityName = "",
                        activityDescription = "",
                        activityTicked = false,

                        startingTime = "",
                        endingTime = "",

                        startingDate = "",
                        endingDate = "",

                        )
                }

            }

            //Basic Information
            is DatabaseDeadlineEvent.SetDataType -> {
                _state.update { it.copy(
                    dataType = event.dataType
                ) }
            }

            is DatabaseDeadlineEvent.SetActivityName -> {
                _state.update { it.copy(
                    activityName = event.activityName
                ) }
            }

            is DatabaseDeadlineEvent.SetActivityDescription -> {
                _state.update { it.copy(
                    activityDescription = event.activityDescription
                ) }
            }

            is DatabaseDeadlineEvent.SetActivityTicked -> {
                _state.update { it.copy(
                    activityTicked = event.activityTicked
                ) }
            }

            is DatabaseDeadlineEvent.SetActivityStartingTime -> {
                _state.update { it.copy(
                    startingTime = event.activityStartingTime
                ) }
            }

            is DatabaseDeadlineEvent.SetActivityEndingTime -> {
                _state.update { it.copy(
                    endingTime = event.activityEndingTime
                ) }
            }

            is DatabaseDeadlineEvent.SetActivityStartingDate -> {
                _state.update { it.copy(
                    startingDate = event.activityStartingDate
                ) }
            }

            is DatabaseDeadlineEvent.SetActivityEndingDate -> {
                _state.update { it.copy(
                    endingDate = event.activityEndingDate
                ) }
            }

            //Updating
            is DatabaseDeadlineEvent.UpdateActivityTicked -> {
                viewModelScope.launch {
                    val existingData = dao.getDataById(event.dataId)
                    val updatedData = existingData.copy(
                        activityTicked = event.UpdateActivityTicked
                    )
                    onUpdateDeadline(updatedData)
                }
            }

            //Sorting System
            is DatabaseDeadlineEvent.SortingSystem -> {
                _sortType.value = event.sortType
            }

        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class DatabaseAdminViewModel(
    private val dao: DatabaseAdminDao
): ViewModel() {

    init {
        Log.d("DatabaseAdminViewModel", "ViewModel instance created: ${this.hashCode()}")
    }

    private val _sortType = MutableStateFlow(DatabaseAdminSortType.CHRONOLOGICAL)
    private val _notice = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                DatabaseAdminSortType.CHRONOLOGICAL -> dao.orderByDateAdded()
                DatabaseAdminSortType.BY_TITLE -> dao.orderByNoticeTitle()
                DatabaseAdminSortType.BY_ADMIN -> dao.orderByAdminName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(DatabaseAdminState())
    val state = combine(_state, _sortType, _notice) { state, sortType, notice ->
        state.copy(
            data = notice,
            sortType = sortType
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DatabaseAdminState())

    init {
        viewModelScope.launch {
            if (dao.getCount() == 0) {
                prepopulateDatabase()
            }
        }
    }

    private suspend fun prepopulateDatabase() {
        dao.insertOrUpdateNewData(
            DatabaseAdminData(
                dataType = "Admin",
                adminName = "Capstone II Group 4",
                noticeTitle = "Welcome!",
                noticeImage = R.drawable.udm_logo,
                noticeBody = "Welcome to the UDM Student Assistant!"
            )
        )
    }

    fun onEvent(event: DatabaseAdminEvent) {
        when(event) {
            is DatabaseAdminEvent.DeleteNotice -> {
                viewModelScope.launch {
                    dao.deleteOldData(event.data)
                }
            }
            DatabaseAdminEvent.SaveData -> {
                val dataType = state.value.dataType
                val adminName = state.value.adminName
                val noticeTitle = state.value.noticeTitle
                val noticeImage = state.value.noticeImage
                val noticeBody = state.value.noticeBody

                if (adminName.isBlank() or noticeTitle.isBlank()){
                    return
                }

                val data = DatabaseAdminData(
                    dataType = dataType,
                    adminName = adminName,
                    noticeImage = noticeImage,
                    noticeTitle = noticeTitle,
                    noticeBody = noticeBody

                )
                viewModelScope.launch {
                    dao.insertOrUpdateNewData(data)
                }
                _state.update {
                    it.copy(
                        dataType = "",
                        adminName = "",
                        noticeTitle = "",
                        noticeImage = 0,
                        noticeBody = ""
                    )
                }

            }

            //Basic Information
            is DatabaseAdminEvent.SetDataType -> {
                _state.update { it.copy(
                    dataType = event.dataType
                ) }
            }

            is DatabaseAdminEvent.SetNoticeTitle -> {
                _state.update { it.copy(
                    noticeTitle = event.noticeTitle
                ) }
            }

            is DatabaseAdminEvent.SetNoticeImage -> {
                _state.update { it.copy(
                    noticeImage = event.noticeImage
                ) }
            }

            is DatabaseAdminEvent.SetNoticeBody -> {
                _state.update { it.copy(
                    noticeBody = event.noticeBody
                ) }
            }

            is DatabaseAdminEvent.SetAdminName -> {
                _state.update { it.copy(
                    adminName = event.adminName
                ) }
            }

            //Sorting System
            is DatabaseAdminEvent.SortingSystem -> {
                _sortType.value = event.sortType
            }

        }
    }
}