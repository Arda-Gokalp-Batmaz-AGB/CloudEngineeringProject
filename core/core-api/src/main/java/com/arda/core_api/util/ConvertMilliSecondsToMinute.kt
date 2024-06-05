package com.arda.core_api.util

import java.util.Locale

class ConvertMilliSecondsToMinute {
    companion object {
        operator fun invoke(time : Number): Double {
            if(time == 0)
                return 0.0
            return String.format(Locale.US, "%.1f", time.toDouble() / (1000 * 60)).toDouble()
        }
    }
}
