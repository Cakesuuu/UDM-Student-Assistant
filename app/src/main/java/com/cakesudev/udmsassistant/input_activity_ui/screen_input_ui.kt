package com.cakesudev.udmsassistant.input_activity_ui

import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.cakesudev.udmsassistant.R
import com.cakesudev.udmsassistant.database.DatabaseAdminEvent
import com.cakesudev.udmsassistant.database.DatabaseAdminState
import com.cakesudev.udmsassistant.database.DatabaseDeadlineEvent
import com.cakesudev.udmsassistant.database.DatabaseDeadlineState
import com.cakesudev.udmsassistant.database.DatabaseNoteEvent
import com.cakesudev.udmsassistant.database.DatabaseNoteState
import com.cakesudev.udmsassistant.database.DatabaseScheduleEvent
import com.cakesudev.udmsassistant.database.DatabaseScheduleState
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.theme_grey
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import com.cakesudev.udmsassistant.ui.theme.theme_grey_darker
import com.cakesudev.udmsassistant.ui.theme.udm_green
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white
import com.cakesudev.udmsassistant.ui.theme.white_faded
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

var UI_Title: String = "UI_TITLE_PLACEHOLDER"

var Variable_DataType: String = "DATA_TYPE"

@Composable
fun Component_TopBar(
    TopBarTitle: String
){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = TopBarTitle,
                color = white,
                fontFamily = FontAlataFamily
            )
        }

    }
}

//IME
@Composable
fun rememberIMEState(): State<Boolean> {
    val imeState = remember { mutableStateOf(false)}

    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            imeState.value = isKeyboardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return imeState
}

//WEEKDAYS
@Composable
fun Component_WeekdayButton(
    dayText: String,
    onClick: (Boolean) -> Unit,
    isChecked: Boolean,
    modifier: Modifier
){

    val tint by animateColorAsState(if (isChecked) udm_green_light else theme_grey_dark)

    Column(
        modifier
    ) {
        IconToggleButton(
            checked = isChecked,
            onCheckedChange = { onClick(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = theme_grey_dark)
                .border(1.dp, udm_green_light, RoundedCornerShape(10.dp))
                .background(tint)
        ) {
            Text(
                dayText,
                color = white,
                fontSize = 12.sp,
                modifier = Modifier,
                fontFamily = FontAlataFamily,
                maxLines = 1
            )
        }
    }
}

@Composable
fun Component_TimePickerButton(
    typeText: String,
    dialogTitle: String,
    modifier: Modifier,
    onTimeSelected: (String) -> Unit
){

    var DataTime by remember { mutableStateOf(LocalTime.NOON)}
    val timeDialogState = rememberMaterialDialogState()

    val formattedTimeData by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm a")
                .format(DataTime) }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.icon_schedule),
                contentDescription = "Icon",
                colorFilter = ColorFilter.tint(white)
            )

            TextButton(
                onClick = {
                    timeDialogState.show()
                }
            ) {
                Text(
                    text = typeText,
                    color = white,
                    fontFamily = FontAlataFamily
                )
            }
        }

    }
    MaterialDialog(
        dialogState = timeDialogState,
        backgroundColor = theme_grey_dark,
        buttons = {
            positiveButton(
                text = "Submit",
                textStyle = TextStyle(
                    color = colorResource(
                        id = R.color.udm_green_light
                    )
                )
            )
            negativeButton(text = "Cancel",
                textStyle = TextStyle(
                    color = colorResource(
                        id = R.color.udm_green_light
                    )
                )
            )
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = dialogTitle,
            colors = TimePickerDefaults.colors(
                activeBackgroundColor = theme_grey,
                activeTextColor = white,
                headerTextColor = white,
                borderColor = udm_green_light,
                inactiveTextColor = white_faded,
                selectorColor = udm_green_light,
                inactiveBackgroundColor = theme_grey_darker,
                selectorTextColor = white)
        ) {
            DataTime = it
            onTimeSelected(formattedTimeData)
        }
    }
}

@Composable
fun Component_DateSelector(
    typeText: String,
    dialogTitle: String,
    modifier: Modifier,
    onTimeSelected: (String) -> Unit
){
    var DataDate by remember { mutableStateOf(LocalDate.now()) }
    val dateDialogState = rememberMaterialDialogState()

    val formattedDateData by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMMM d, yyyy")
                .format(DataDate) }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.icon_calendar),
                contentDescription = "Icon",
                colorFilter = ColorFilter.tint(white)
            )

            TextButton(
                onClick = {
                    dateDialogState.show()
                }
            ) {
                Text(
                    text = typeText,
                    color = white,
                    fontFamily = FontAlataFamily
                )
            }
        }

    }
    MaterialDialog(
        dialogState = dateDialogState,
        backgroundColor = theme_grey_dark,
        buttons = {
            positiveButton(
                text = "Submit",
                textStyle = TextStyle(
                    color = colorResource(
                        id = R.color.udm_green_light
                    )
                )
            )
            negativeButton(text = "Cancel",
                textStyle = TextStyle(
                    color = colorResource(
                        id = R.color.udm_green_light
                    )
                )
            )
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = dialogTitle,
            colors = DatePickerDefaults.colors(
                dateActiveBackgroundColor = udm_green_light,
                dateActiveTextColor = white,
                dateInactiveTextColor = white_faded,
                dateInactiveBackgroundColor = theme_grey,
                calendarHeaderTextColor = white,
                headerTextColor = white,
                headerBackgroundColor = udm_green_light
            )
        ) {
            DataDate = it
            onTimeSelected(formattedDateData)
        }
    }
}

@Composable
fun Component_SubmitButton(
    onClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = theme_grey_dark)
                .border(1.dp, udm_green_light, RoundedCornerShape(10.dp))
                .background(udm_green_light)
        ) {
            Text(
                text = "Submit",
                color = white,
                fontFamily = FontAlataFamily
            )
        }
    }
}

var isScheduleSubjectNameFilled: Boolean = false
var isScheduleStartingTimeFilled: Boolean = false

var isNoteTitleFilled: Boolean = false
var isNoteBodyFilled: Boolean = false

var isActivityNameFilled: Boolean = false
var isActivityDateFilled: Boolean = false

@Composable
fun UI_Input(
    navController: NavController,
    scheduleState: DatabaseScheduleState,
    scheduleEvent: (DatabaseScheduleEvent) -> Unit,
    deadlineState: DatabaseDeadlineState,
    deadlineEvent: (DatabaseDeadlineEvent) -> Unit,
    noteState: DatabaseNoteState,
    noteEvent: (DatabaseNoteEvent) -> Unit,
    adminState: DatabaseAdminState,
    adminEvent: (DatabaseAdminEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column() {

        Component_TopBar(TopBarTitle = UI_Title)
        when (Variable_DataType) {
            "Schedule" -> {
                UI_ScheduleInput(scheduleState = scheduleState, scheduleEvent = scheduleEvent, navController = navController)
            }
            "Deadline" -> {
                UI_DeadlineInput(deadlineState = deadlineState, deadlineEvent = deadlineEvent, navController = navController)
            }
            "Note" -> {
                UI_NoteInput(noteState = noteState, noteEvent = noteEvent, navController = navController)
            }
            "Admin" -> {
                UI_AdminInput(adminState = adminState, adminEvent = adminEvent, navController = navController)
            }
        }

    }

}