package com.cakesudev.udmsassistant.database.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.cakesudev.udmsassistant.R

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        println("Alarm triggered: $message")
        playSound(context)
    }

    private fun playSound(context: Context?) {
        try {
            val mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}