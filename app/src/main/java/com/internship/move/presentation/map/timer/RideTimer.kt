package com.internship.move.presentation.map.timer

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import kotlin.math.roundToInt

class RideTimer(context: Context, private val activity: FragmentActivity) {

    private var timerStarted = false
    private var time = 0.0
    private val serviceIntent = Intent(context,TimerService::class.java)

    fun startStopTimer() {
        if (timerStarted) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        activity.startService(serviceIntent)
        timerStarted = true
    }

    private fun stopTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        activity.stopService(serviceIntent)
        timerStarted = false
    }

    fun getTimeStringFromDoubleExtended(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeStringExtended(hours, minutes, seconds)
    }

    fun getTimeStringFromDoubleCollapsed(time: Double): String {
        val resultInt = time.roundToInt()
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeStringCollapsed(minutes, seconds)
    }

    private fun makeTimeStringExtended(hours: Int, minutes: Int, seconds: Int): String =
        String.format("%02d:%02d:%02d", hours, minutes, seconds)

    private fun makeTimeStringCollapsed(minutes: Int, seconds: Int): String = String.format("%02d:%02d", minutes, seconds)

}