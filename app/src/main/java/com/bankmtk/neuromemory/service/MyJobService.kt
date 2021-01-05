package com.bankmtk.neuromemory.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.alert.AlertActivity
import java.util.*

class MyJobService: JobService() {
    companion object {
        private const val TAG = "MyJobService"
        private const val TIME_SLEEP_MILLISECONDS: Long = 10000
    }
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.bankmtk.neuromemory.service.MyJobService"
    private val description = "NotificationAlert"

    private var jobCanceled : Boolean = false

    private fun doBackgroundWork(p0: JobParameters?) {
        Thread{
            kotlin.run {
                for (i: Int in 0 until 9) {
                    notifyUser()
                    if (jobCanceled) {
                        return@run
                    }

                    Thread.sleep(TIME_SLEEP_MILLISECONDS)
                }
                //jobFinished(p0, false)
            }
        }.start()
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        doBackgroundWork(p0)
        //notifyUser()
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        jobCanceled = true
        return true
    }
    private fun isHaveItem(data: List<Sticker>?):Boolean {
        for(i in data!!.indices){
            if (data[i].lastChanged< Date()) {
                return true
            }
        }
        return false
    }

    private fun notifyUser(){
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(applicationContext, AlertActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(
                channelId,
                description,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContentTitle("StatusService:")
                .setContentText("Neuron is waiting for confirmation")
                .setSmallIcon(R.drawable.notify_back)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.notify_back))
                .setContentIntent(pendingIntent)
        } else{
            builder = Notification.Builder(this)
                .setContentTitle("Status:")
                .setContentText("Neuron is waiting for confirmation")
                .setSmallIcon(R.drawable.notify_back)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.notify_back))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(123456, builder.build())
    }
}