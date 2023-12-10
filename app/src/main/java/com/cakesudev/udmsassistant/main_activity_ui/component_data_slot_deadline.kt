package com.cakesudev.udmsassistant.main_activity_ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakesudev.udmsassistant.R
import com.cakesudev.udmsassistant.database.DatabaseDeadlineEvent
import com.cakesudev.udmsassistant.database.DatabaseDeadlineState
import com.cakesudev.udmsassistant.database.DatabaseScheduleEvent
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white

@Composable
fun Component_Data_Slot_Deadline(
    deadlineState: DatabaseDeadlineState,
    deadlineEvent: (DatabaseDeadlineEvent) -> Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(deadlineState.data) { data ->

            var borderColor by remember { mutableStateOf(udm_green_light) }
            var strike by remember { mutableStateOf(TextDecoration.None) }
            var update by remember { mutableStateOf(data.activityTicked) }

            Column(
                modifier = Modifier.clickable {
                    if(data.activityTicked == false){
                        update = true
                        deadlineEvent(DatabaseDeadlineEvent.UpdateActivityTicked(
                            dataId = data.id,
                            UpdateActivityTicked = update
                        ))
                    } else {
                        update = false
                        deadlineEvent(DatabaseDeadlineEvent.UpdateActivityTicked(
                            dataId = data.id,
                            UpdateActivityTicked = update
                        ))
                    }
                }
            ) {

                if (update) {
                    borderColor = white
                    strike = TextDecoration.LineThrough
                } else {
                    borderColor = udm_green_light
                    strike = TextDecoration.None
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    /*IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Deadline",
                            tint = colorResource(id = R.color.white)
                        )
                    }*/

                    IconButton(
                        onClick = { deadlineEvent(DatabaseDeadlineEvent.DeleteDeadline(data)) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Deadline",
                            tint = colorResource(id = R.color.white)
                        )
                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 0.dp)
                        .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text =
                        if(data.startingTime.isBlank()){
                            data.startingDate } else {
                                "${data.startingDate} at ${data.startingTime}" },
                        fontSize = 12.sp,
                        color = white,
                        fontFamily = FontAlataFamily
                    )

                    Row {
                        Text(
                            text = if(data.activityTicked) {"✓ "} else {""},
                            fontSize = 20.sp,
                            color = white,
                            fontFamily = FontAlataFamily,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = data.activityName,
                            fontSize = 20.sp,
                            color = white,
                            fontFamily = FontAlataFamily,
                            textAlign = TextAlign.Center,
                            textDecoration = strike
                        )
                    }

                    Text(
                        text = data.activityDescription,
                        fontSize = 12.sp,
                        color = white,
                        fontFamily = FontAlataFamily
                    )

                }
            }

        }

        item { Spacer(modifier = Modifier.padding(40.dp)) }

    }

}