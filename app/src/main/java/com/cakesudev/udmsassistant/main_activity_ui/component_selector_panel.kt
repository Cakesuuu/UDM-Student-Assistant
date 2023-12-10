package com.cakesudev.udmsassistant.main_activity_ui

import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cakesudev.udmsassistant.R
import com.cakesudev.udmsassistant.database.DatabaseDeadlineEvent
import com.cakesudev.udmsassistant.database.DatabaseDeadlineState
import com.cakesudev.udmsassistant.database.DatabaseNoteEvent
import com.cakesudev.udmsassistant.database.DatabaseNoteState
import com.cakesudev.udmsassistant.database.DatabaseScheduleEvent
import com.cakesudev.udmsassistant.database.DatabaseScheduleState
import com.cakesudev.udmsassistant.input_activity_ui.UI_Title
import com.cakesudev.udmsassistant.input_activity_ui.Variable_DataType
import com.cakesudev.udmsassistant.ui.theme.black_faded
import com.cakesudev.udmsassistant.ui.theme.clear
import com.cakesudev.udmsassistant.ui.theme.theme_grey
import com.cakesudev.udmsassistant.ui.theme.white_faded

@Composable
fun Iconizer(
    icon: Int,
    onTap: () -> Unit
){
    Column() {
        Image(
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
                .clickable(onClick = onTap),
            painter = painterResource(icon),
            contentDescription = "icon",
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Composable
fun BottomPickerButtons(
    scheduleState: DatabaseScheduleState,
    scheduleEvent: (DatabaseScheduleEvent) -> Unit,
    deadlineState: DatabaseDeadlineState,
    deadlineEvent: (DatabaseDeadlineEvent) -> Unit,
    noteState: DatabaseNoteState,
    noteEvent: (DatabaseNoteEvent) -> Unit,
    navController: NavController
){
    val brush = Brush.verticalGradient(listOf(clear, black_faded))

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            Modifier
                .background(brush)
                .padding(20.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
        ) {

            val context = LocalContext.current

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){

                //UDMSA Logo
                Column() {
                    Image(
                        painter = painterResource(R.drawable.udmsa_icon),
                        contentDescription = "Bulok",
                        Modifier
                            .padding(10.dp)
                            .size(60.dp)
                            .background(
                                color = theme_grey,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(1.dp, white_faded, RoundedCornerShape(10.dp))
                            .clickable{
                                navController.navigate("UI_Credit_Screen")
                            }
                    )
                }

                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(
                            color = theme_grey,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(1.dp, white_faded, RoundedCornerShape(10.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Iconizer(
                        icon = R.drawable.icon_schedule,
                        onTap = {
                            navController.navigate("Input_UI_Screen")
                            UI_Title = "Set New Schedule"
                            Variable_DataType = "Schedule"
                            scheduleEvent(DatabaseScheduleEvent.SetDataType(Variable_DataType))
                        }
                    )
                    Iconizer(
                        icon = R.drawable.icon_to_do_list,
                        onTap = {
                            navController.navigate("Input_UI_Screen")
                            UI_Title = "Set New Deadline"
                            Variable_DataType = "Deadline"
                            deadlineEvent(DatabaseDeadlineEvent.SetDataType(Variable_DataType))
                        }
                    )
                    Iconizer(
                        icon = R.drawable.icon_notes,
                        onTap = {
                            navController.navigate("Input_UI_Screen")
                            UI_Title = "Set New Note"
                            Variable_DataType = "Note"
                            noteEvent(DatabaseNoteEvent.SetDataType(Variable_DataType))
                        }
                    )
                }


            }

        }

    }

}