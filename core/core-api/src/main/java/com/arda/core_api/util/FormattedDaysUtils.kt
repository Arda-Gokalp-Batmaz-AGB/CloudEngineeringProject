package com.arda.core_api.util

import com.arda.core_api.domain.model.WeekDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FormattedDaysUtils {
    companion object {
        fun getFormattedDateGivenDaysAgo(numberOfDayBefore : Long): String {
            val sevenDaysAgo = LocalDate.now().minusDays(numberOfDayBefore)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return sevenDaysAgo.format(formatter)
        }
        fun getFormattedDateToday(): String {
            val today = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return today.format(formatter)
        }
        fun getDatesAndWeekDays(numberOfDaysBefore: Long): List<Pair<String, WeekDay>> {
            val today = LocalDate.now()
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val datesAndWeekDays = mutableListOf<Pair<String, WeekDay>>()

            for (i in 0 until numberOfDaysBefore) {
                val date = today.minusDays(i)
                val formattedDate = date.format(dateFormatter)
                //val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
                val dayOfWeek = WeekDay.fromDayNumber(date.dayOfWeek.value)
                datesAndWeekDays.add(Pair(formattedDate, dayOfWeek))
            }

            return datesAndWeekDays
        }
    }
}