package com.cakesudev.udmsassistant.database.alarm

import java.time.DayOfWeek
import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String
)