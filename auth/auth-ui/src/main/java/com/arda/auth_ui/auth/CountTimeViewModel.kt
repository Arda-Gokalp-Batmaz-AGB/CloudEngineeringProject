package com.arda.dystherapy.ui.auth

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CountTimeViewModel : ViewModel()  {
    companion object VerifyButtonTimer
    {
        val cooldownTimeSeconds = 60
        var isButtonActive by mutableStateOf(true)
        var remainingTime by mutableStateOf(0)

        fun startTimer()
        {
            if(isButtonActive == true)
            {
                remainingTime = cooldownTimeSeconds
                isButtonActive = false
                object: CountDownTimer((cooldownTimeSeconds *1000).toLong(), 1000){
                    override fun onTick(untilTime: Long) {
                        remainingTime = (untilTime / 1000).toInt()
                    }
                    override fun onFinish() {
                        remainingTime = 0
                        isButtonActive = true
                    }
                }.start()
            }

        }
    }

}