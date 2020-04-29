package com.bankmtk.neuromemory.ui.splash

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.bankmtk.neuromemory.ui.splash.StarFragment.Companion.newInstance

class StarActivity: SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return newInstance()
    }
    companion object{
        fun getStartIntent(context: Context) = Intent(context,
            StarActivity::class.java)
    }
}