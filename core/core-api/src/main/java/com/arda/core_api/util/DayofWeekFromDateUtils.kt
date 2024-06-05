package com.arda.core_api.util

import com.arda.core_api.domain.model.WeekDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayofWeekFromDateUtils {
    companion object {
        operator fun invoke(date: String): WeekDay {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(date, formatter)
            return WeekDay.fromDayNumber(date.dayOfWeek.value)
        }
    }
}