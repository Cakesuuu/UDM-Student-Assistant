package com.cakesudev.udmsassistant.input_activity_ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cakesudev.udmsassistant.database.DatabaseNoteEvent
import com.cakesudev.udmsassistant.database.DatabaseScheduleEvent
import com.cakesudev.udmsassistant.database.DatabaseScheduleState
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UI_ScheduleInput(
    navController: NavController,
    scheduleState: DatabaseScheduleState,
    scheduleEvent: (DatabaseScheduleEvent) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        //Subject Name
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ){
            Text(
                text = "Subject",
                color = white,
                fontFamily = FontAlataFamily
            )

            var Subject_Name by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_dark)
                    .padding(top = 5.dp),
                value = Subject_Name,
                onValueChange = {
                        Subject_Name = it
                        scheduleEvent(DatabaseScheduleEvent.SetSubjectName(it))
                        isScheduleSubjectNameFilled = true },
                placeholder = { Text("Enter subject here.") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = white,
                    unfocusedBorderColor = udm_green_light
                )
            )

            val context = LocalContext.current

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(3.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                maxItemsInEachRow = 3
            ) {

                var startPlaceholder by remember { mutableStateOf("Set starting time") }
                var endPlaceholder by remember { mutableStateOf("Set ending time") }

                Component_TimePickerButton(
                    typeText = startPlaceholder,
                    dialogTitle = "Set starting time for schedule",
                    modifier = Modifier
                        .weight(1f),
                    onTimeSelected = {
                        scheduleEvent(DatabaseScheduleEvent.SetStartingTime(it))
                        startPlaceholder = "Starts at $it"
                        isScheduleStartingTimeFilled = true
                    }
                )

                Component_TimePickerButton(
                    typeText = endPlaceholder,
                    dialogTitle = "Set ending time for schedule",
                    modifier = Modifier
                        .weight(1f),
                    onTimeSelected = {
                        scheduleEvent(DatabaseScheduleEvent.SetEndingTime(it))
                        endPlaceholder = "Ends at $it"
                    }
                )

            }

        }

        //Weekday Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Select Weekdays",
                color = white,
                fontFamily = FontAlataFamily
            )

            val context = LocalContext.current

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(3.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                maxItemsInEachRow = 5
            ) {

                var MonToggle by remember { mutableStateOf(false) }
                var TueToggle by remember { mutableStateOf(false) }
                var WedToggle by remember { mutableStateOf(false) }
                var ThuToggle by remember { mutableStateOf(false) }
                var FriToggle by remember { mutableStateOf(false) }
                var SatToggle by remember { mutableStateOf(false) }
                var SunToggle by remember { mutableStateOf(false) }

                Component_WeekdayButton(
                    dayText = "Mon",
                    isChecked = MonToggle,
                    onClick = {
                        MonToggle = it
                        scheduleEvent(DatabaseScheduleEvent.SetMondayToggle(it))
                    },
                    modifier = Modifier
                        .weight(1f)
                )

                Component_WeekdayButton(
                    dayText = "Tue",
                    isChecked = TueToggle,
                    onClick = {
                        TueToggle = it
                        scheduleEvent(DatabaseScheduleEvent.SetTuesdayToggle(it))
                    },
                    modifier = Modifier
                        .weight(1f)
                )

                Component_WeekdayButton(
                    dayText = "Wed",
                    isChecked = WedToggle,
                    onClick = {
                        WedToggle = it
                        scheduleEvent(DatabaseScheduleEvent.SetWednesdayToggle(it))
                    },
                    modifier = Modifier
                        .weight(1f)
                )

                Component_WeekdayButton(
                    dayText = "Thu",
                    isChecked = ThuToggle,
                    onClick = {
                        ThuToggle = it
                        scheduleEvent(DatabaseScheduleEvent.SetThursdayToggle(it))
                    },
                    modifier = Modifier
                        .weight(1f)
                )

                Component_WeekdayButton(
                    dayText = "Fri",
                    isChecked = FriToggle,
                    onClick = {
                        FriToggle = it
                        scheduleEvent(DatabaseScheduleEvent.SetFridayToggle(it))
                    },
                    modifier = Modifier
                        .weight(1f)
                )

                Component_WeekdayButton(
                    dayText = "Sat",
                    isChecked = SatToggle,
                    onClick = {
                        SatToggle = it
                        scheduleEvent(DatabaseScheduleEvent.SetSaturdayToggle(it))
                    },
                    modifier = Modifier
                        .weight(1f)
                )

                Component_WeekdayButton(
                    dayText = "Sun",
                    isChecked = SunToggle,
                    onClick = {
                        SunToggle = it
                        scheduleEvent(DatabaseScheduleEvent.SetSundayToggle(it))
                    },
                    modifier = Modifier
                        .weight(1f)
                )

            }
        }

        //Teacher's Name
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Instructor",
                color = white,
                fontFamily = FontAlataFamily
            )

            var Instructor_Name by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_dark)
                    .padding(top = 5.dp),
                value = Instructor_Name,
                onValueChange = {
                    Instructor_Name = it
                    scheduleEvent(DatabaseScheduleEvent.SetInstructorName(it))
                                },
                placeholder = { Text("Enter Instructor's name here.") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = white,
                    unfocusedBorderColor = udm_green_light
                )
            )

        }

        //Room Name
        Column(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Room Name",
                color = white,
                fontFamily = FontAlataFamily
            )

            var Room_Name by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_dark)
                    .padding(top = 5.dp),
                value = Room_Name,
                onValueChange = {
                    Room_Name = it
                    scheduleEvent(DatabaseScheduleEvent.SetRoomName(it))
                    },
                placeholder = { Text("Enter designated room name here.") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = white,
                    unfocusedBorderColor = udm_green_light
                )
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp)
        ) {
            val context = LocalContext.current

            Component_SubmitButton(
                onClick = {

                    if (isScheduleSubjectNameFilled == false || isScheduleStartingTimeFilled == false) {
                        Toast.makeText(
                            context,
                            "Please enter a subject name or starting time.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Component_SubmitButton
                    }

                    scheduleEvent(DatabaseScheduleEvent.SaveData)
                    Variable_DataType = ""
                    isScheduleSubjectNameFilled = false
                    isScheduleStartingTimeFilled = false
                    navController.popBackStack()

                }

            )

        }

    }
}