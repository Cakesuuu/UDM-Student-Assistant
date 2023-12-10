package com.cakesudev.udmsassistant.input_activity_ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cakesudev.udmsassistant.database.DatabaseDeadlineEvent
import com.cakesudev.udmsassistant.database.DatabaseDeadlineState
import com.cakesudev.udmsassistant.database.DatabaseScheduleEvent
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UI_DeadlineInput(
    navController: NavController,
    deadlineState: DatabaseDeadlineState,
    deadlineEvent: (DatabaseDeadlineEvent) -> Unit,
    modifier: Modifier = Modifier
){

    val imeState = rememberIMEState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value){
        if (imeState.value){
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        //Deadline Name
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ){
            Text(
                text = "Activity",
                color = white,
                fontFamily = FontAlataFamily
            )

            var Activity_Name by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_dark)
                    .padding(top = 5.dp),
                value = Activity_Name,
                onValueChange = {
                    Activity_Name = it
                    deadlineEvent(DatabaseDeadlineEvent.SetActivityName(it))
                    isActivityNameFilled = true },
                placeholder = { Text("Enter activity name here.") },
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

                var placeholderDate by remember { mutableStateOf("Set deadline date") }

                Component_DateSelector(
                    typeText = placeholderDate,
                    dialogTitle = "Set date for deadline",
                    modifier = Modifier
                        .weight(1f),
                    onTimeSelected = {
                        deadlineEvent(DatabaseDeadlineEvent.SetActivityStartingDate(it))
                        placeholderDate = "$it"
                        isActivityDateFilled = true
                    }
                )

                var placeholderTime by remember { mutableStateOf("Set deadline time") }

                Component_TimePickerButton(
                    typeText = placeholderTime,
                    dialogTitle = "Set time for schedule",
                    modifier = Modifier
                        .weight(1f),
                    onTimeSelected = {
                        deadlineEvent(DatabaseDeadlineEvent.SetActivityStartingTime(it))
                        placeholderTime = "Ends at $it"
                    }
                )

            }
        }

        //Activity Description
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ){
            Text(
                text = "Description",
                color = white,
                fontFamily = FontAlataFamily
            )

            var Activity_Description by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = theme_grey_dark)
                    .padding(top = 5.dp, bottom = 10.dp),
                value = Activity_Description,
                onValueChange = {
                    Activity_Description = it
                    deadlineEvent(DatabaseDeadlineEvent.SetActivityDescription(it))
                },
                placeholder = { Text("Enter description here (Optional).") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = white,
                    unfocusedBorderColor = udm_green_light
                )
            )

            val context = LocalContext.current

            Component_SubmitButton(
                onClick = {

                    if (isActivityDateFilled == false || isActivityNameFilled == false) {
                        Toast.makeText(
                            context,
                            "Please enter an activity name and a date.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Component_SubmitButton
                    }

                    deadlineEvent(DatabaseDeadlineEvent.SaveData)
                    Variable_DataType = ""
                    isActivityNameFilled = false
                    isActivityDateFilled = false
                    navController.popBackStack()

                }

            )

        }

    }
}