package com.internship.move.presentation.map.timer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class TimerService : Service() {

    private val timer = Timer()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(TIME_EXTRA, 0.0)

        timer.scheduleAtFixedRate(TimeTask(time), TIMER_DELAY, TIME_PERIOD)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Double) : TimerTask() {

        override fun run() {
            val intent = Intent(TIMER_UPDATED)
            time++
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }
    }

    companion object {
        const val TIMER_DELAY = 0L
        const val TIME_PERIOD = 1000L
        const val TIME_EXTRA = "TIME_EXTRA"
        const val TIMER_UPDATED = "TIMER_UPDATED"
    }
}