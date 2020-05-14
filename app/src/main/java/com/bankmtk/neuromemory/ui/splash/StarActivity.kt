package com.bankmtk.neuromemory.ui.splash

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.bankmtk.neuromemory.ui.splash.StarFragment.Companion.newInstance
import org.jetbrains.anko.toast

class StarActivity: SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return newInstance()
    }
    companion object{
        fun getStartIntent(context: Context) = Intent(context,
            StarActivity::class.java)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        toast(message = "Have a nice day!")
    }
}