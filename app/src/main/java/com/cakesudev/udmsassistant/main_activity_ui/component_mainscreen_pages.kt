package com.cakesudev.udmsassistant.main_activity_ui

import StartTabLayout
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.cakesudev.udmsassistant.database.DatabaseAdminEvent
import com.cakesudev.udmsassistant.database.DatabaseAdminState
import com.cakesudev.udmsassistant.database.DatabaseDeadlineEvent
import com.cakesudev.udmsassistant.database.DatabaseDeadlineState
import com.cakesudev.udmsassistant.database.DatabaseNoteEvent
import com.cakesudev.udmsassistant.database.DatabaseNoteState
import com.cakesudev.udmsassistant.database.DatabaseScheduleEvent
import com.cakesudev.udmsassistant.database.DatabaseScheduleState
import com.cakesudev.udmsassistant.main_activity_ui.admin_panel.Component_Data_Slot_Admin
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.white

@Composable
fun ScheduleScreen(
    scheduleState: DatabaseScheduleState,
    scheduleEvent: (DatabaseScheduleEvent) -> Unit
){

    Component_Data_Slot_Schedule(scheduleState = scheduleState, scheduleEvent = scheduleEvent)

}

@Composable
fun DeadlineScreen(
    deadlineState: DatabaseDeadlineState,
    deadlineEvent: (DatabaseDeadlineEvent) -> Unit
){

    Component_Data_Slot_Deadline(deadlineState = deadlineState, deadlineEvent = deadlineEvent)
}

@Composable
fun NotesScreen(
    noteState: DatabaseNoteState,
    noteEvent: (DatabaseNoteEvent) -> Unit
){

    Component_Data_Slot_Note(noteState = noteState, noteEvent = noteEvent)

}

@Composable
fun OfficialScreen(
    adminState: DatabaseAdminState,
    adminEvent: (DatabaseAdminEvent) -> Unit
){
    Component_Data_Slot_Admin(adminState = adminState, adminEvent = adminEvent)
}

@Composable
fun MainScreen(
    noteState: DatabaseNoteState,
    noteEvent: (DatabaseNoteEvent) -> Unit,
    deadlineState: DatabaseDeadlineState,
    deadlineEvent: (DatabaseDeadlineEvent) -> Unit,
    scheduleState: DatabaseScheduleState,
    scheduleEvent: (DatabaseScheduleEvent) -> Unit,
    adminState: DatabaseAdminState,
    adminEvent: (DatabaseAdminEvent) -> Unit,
    navController: NavController
){
    StartTabLayout(
        noteState = noteState,
        noteEvent = noteEvent,
        scheduleEvent = scheduleEvent,
        scheduleState = scheduleState,
        deadlineEvent = deadlineEvent,
        deadlineState = deadlineState,
        adminState = adminState,
        adminEvent = adminEvent)

    BottomPickerButtons(
        navController = navController,
        scheduleState = scheduleState,
        scheduleEvent = scheduleEvent,
        noteState = noteState,
        noteEvent = noteEvent,
        deadlineState = deadlineState,
        deadlineEvent = deadlineEvent)
}