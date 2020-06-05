package com.bankmtk.neuromemory.ui.splash

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bankmtk.neuromemory.R
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
        val myToast = Toast.makeText(this,R.string.start_notifications, Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.BOTTOM,0,0)
        val toastContainer = myToast.view as LinearLayout
        val myImage = ImageView(this)
        myImage.setImageResource(R.drawable.tree_alert)
        toastContainer.addView(myImage,0)
        toastContainer.setBackgroundColor(Color.TRANSPARENT)
        myToast.show()
    }
}