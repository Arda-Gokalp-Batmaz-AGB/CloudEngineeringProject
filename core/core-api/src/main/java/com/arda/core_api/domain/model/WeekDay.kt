package com.arda.core_api.domain.model

sealed class WeekDay(
    val dayNumber: Int,
) {
    open val day: String
        get() {
            return ""
        }
    open val shortenedNotation: String
        get() {
            return ""
        }


    object Monday : WeekDay(dayNumber=1) {
        override val day: String
            get() = "Monday"
        override val shortenedNotation: String
            get() = "Mon"
    }

    object Tuesday : WeekDay(dayNumber=2) {
        override val day: String
            get() = "Tuesday"
        override val shortenedNotation: String
            get() = "Tue"
    }

    object Wednesday : WeekDay(dayNumber=3) {
        override val day: String
            get() = "Wednesday"
        override val shortenedNotation: String
            get() = "Wed"
    }

    object Thursday : WeekDay(dayNumber=4) {
        override val day: String
            get() = "Thursday"
        override val shortenedNotation: String
            get() = "Thu"
    }

    object Friday : WeekDay(dayNumber=5) {
        override val day: String
            get() = "Friday"
        override val shortenedNotation: String
            get() = "Fri"
    }

    object Saturday : WeekDay(dayNumber=6) {
        override val day: String
            get() = "Saturday"
        override val shortenedNotation: String
            get() = "Sat"
    }

    object Sunday : WeekDay(dayNumber=7) {
        override val day: String
            get() = "Sunday"
        override val shortenedNotation: String
            get() = "Sun"
    }
    companion object {
        fun getShortenedNotationsOfDays() : List<String>{
            return WeekDay::class.sealedSubclasses.map { it.objectInstance as WeekDay }.sortedBy { x-> x.dayNumber }.map { x->
                x.shortenedNotation
            }
        }
        fun fromDayNumber(number: Int): WeekDay {
            return when (number) {
                1 -> Monday
                2 -> Tuesday
                3 -> Wednesday
                4 -> Thursday
                5 -> Friday
                6 -> Saturday
                7 -> Sunday
                else -> throw IllegalArgumentException("Invalid day number: $number")
            }
        }
    }
}

