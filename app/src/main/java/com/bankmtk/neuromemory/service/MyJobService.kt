package com.bankmtk.neuromemory.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import java.util.*

class MyJobService: JobService() {
    companion object {
        private const val TAG = "MyJobService"
        private const val TIME_SLEEP_MILLISECONDS: Long = 1000
    }

    private var jobCanceled : Boolean = false

    private fun doBackgroundWork(p0: JobParameters?) {
        Thread(Runnable {
            kotlin.run {
                for (i: Int in 0 until 9) {

                    if (jobCanceled) {
                        return@run
                    }

                    Thread.sleep(TIME_SLEEP_MILLISECONDS)
                }
                jobFinished(p0, false)
            }
        }).start()
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        doBackgroundWork(p0)
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
}