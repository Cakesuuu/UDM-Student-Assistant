package com.cakesudev.udmsassistant.main_activity_ui

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakesudev.udmsassistant.R
import com.cakesudev.udmsassistant.database.DatabaseDeadlineEvent
import com.cakesudev.udmsassistant.database.DatabaseScheduleEvent
import com.cakesudev.udmsassistant.database.DatabaseScheduleSortType
import com.cakesudev.udmsassistant.database.DatabaseScheduleState
import com.cakesudev.udmsassistant.database.alarm.AlarmItem
import com.cakesudev.udmsassistant.database.alarm.AndroidAlarmScheduler
import com.cakesudev.udmsassistant.ui.theme.FontAlataFamily
import com.cakesudev.udmsassistant.ui.theme.theme_grey_dark
import com.cakesudev.udmsassistant.ui.theme.theme_grey_darker
import com.cakesudev.udmsassistant.ui.theme.udm_green
import com.cakesudev.udmsassistant.ui.theme.udm_green_light
import com.cakesudev.udmsassistant.ui.theme.white
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Component_Data_Slot_Schedule(
    scheduleState: DatabaseScheduleState,
    scheduleEvent: (DatabaseScheduleEvent) -> Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(scheduleState.data) { data ->

            val context = LocalContext.current

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
                        contentDescription = "Edit Schedule",
                        tint = colorResource(id = R.color.white)
                    )
                }*/

                IconButton(
                    onClick = { scheduleEvent(DatabaseScheduleEvent.DeleteSchedule(data)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Schedule",
                        tint = colorResource(id = R.color.white)
                    )
                }

            }

            var alarmActive by remember { mutableStateOf(data.isAlarmActivated) }
            var borderColor by remember { mutableStateOf(udm_green) }
            var backgroundColor by remember { mutableStateOf(theme_grey_darker) }

            var timeDifferenceInSeconds by remember { mutableStateOf(0L) }

            DisposableEffect(data.startingTime) {
                val formatter = DateTimeFormatter.ofPattern("hh:mm a")
                val parsedTime = LocalTime.parse(data.startingTime, formatter)
                val currentTime = LocalTime.now()
                timeDifferenceInSeconds = (parsedTime.toSecondOfDay() - currentTime.toSecondOfDay()).toLong()

                onDispose { /* Cleanup, if needed */ }
            }

            var secondsText = timeDifferenceInSeconds

            val scheduler = AndroidAlarmScheduler(context)
            var alarmItem: AlarmItem? = null

            if (alarmActive == false){
                borderColor = udm_green
                backgroundColor = theme_grey_darker
            } else if (alarmActive == true){
                borderColor = udm_green_light
                backgroundColor = theme_grey_dark
            }

            var message by remember {
                mutableStateOf("Testing")
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 0.dp)
                    .background(color = backgroundColor)
                    .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                    .padding(15.dp)
                    .clickable {
                        if (data.isAlarmActivated == false) {
                            alarmItem = AlarmItem(
                                time = LocalDateTime.now()
                                    .plusSeconds(secondsText.toLong()),
                                message = message
                            )
                            alarmActive = true
                            scheduleEvent(
                                DatabaseScheduleEvent.UpdateAlarmActivated(
                                    dataId = data.id,
                                    updateAlarmActivated = alarmActive
                                )
                            )
                            alarmItem = AlarmItem(
                                time = LocalDateTime.now()
                                    .plusSeconds(secondsText.toLong()),
                                message = message
                            )
                            alarmItem?.let(scheduler::schedule)
                            secondsText = 0
                            message = ""
                        } else {
                            alarmActive = false
                            scheduleEvent(
                                DatabaseScheduleEvent.UpdateAlarmActivated(
                                    dataId = data.id,
                                    updateAlarmActivated = alarmActive
                                )
                            )
                            alarmItem?.let(scheduler::cancel)
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = data.subjectName,
                    fontSize = 20.sp,
                    color = white,
                    fontFamily = FontAlataFamily,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = data.instructorName,
                    fontSize = 12.sp,
                    color = white,
                    fontFamily = FontAlataFamily
                )

                Text(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    text =
                    if (data.endingTime.isNotBlank()) {
                        "${data.startingTime} to ${data.endingTime}"
                    } else {
                        data.startingTime
                    },
                    fontSize = 17.sp,
                    color = white,
                    fontFamily = FontAlataFamily,
                    textAlign = TextAlign.Center
                )

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    maxItemsInEachRow = 5
                ) {
                    DataSlot_WeekDayIndicator(
                        dayText = "Mon",
                        isChecked = data.isMondaySelected,
                        modifier = Modifier
                            .weight(1f)
                    )

                    DataSlot_WeekDayIndicator(
                        dayText = "Tue",
                        isChecked = data.isTuesdaySelected,
                        modifier = Modifier
                            .weight(1f)
                    )

                    DataSlot_WeekDayIndicator(
                        dayText = "Wed",
                        isChecked = data.isWednesdaySelected,
                        modifier = Modifier
                            .weight(1f)
                    )

                    DataSlot_WeekDayIndicator(
                        dayText = "Thu",
                        isChecked = data.isThursdaySelected,
                        modifier = Modifier
                            .weight(1f)
                    )

                    DataSlot_WeekDayIndicator(
                        dayText = "Fri",
                        isChecked = data.isFridaySelected,
                        modifier = Modifier
                            .weight(1f)
                    )

                    DataSlot_WeekDayIndicator(
                        dayText = "Sat",
                        isChecked = data.isSaturdaySelected,
                        modifier = Modifier
                            .weight(1f)
                    )

                    DataSlot_WeekDayIndicator(
                        dayText = "Sun",
                        isChecked = data.isSundaySelected,
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                Text(
                    text = data.roomName,
                    fontSize = 12.sp,
                    color = white,
                    fontFamily = FontAlataFamily
                )

            }

        }

        item { Spacer(modifier = Modifier.padding(40.dp)) }

    }

}

@Composable
fun DataSlot_WeekDayIndicator(
    dayText: String,
    isChecked: Boolean,
    modifier: Modifier
){
    val tint by animateColorAsState(if (isChecked) udm_green_light else theme_grey_dark)

    Column(
        modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = theme_grey_dark)
                .border(1.dp, udm_green_light, RoundedCornerShape(10.dp))
                .background(tint)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
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