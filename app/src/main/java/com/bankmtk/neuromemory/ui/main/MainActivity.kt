package com.bankmtk.neuromemory.ui.main


import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.alert.AlertActivity
import com.bankmtk.neuromemory.ui.base.BaseActivity
import com.bankmtk.neuromemory.ui.splash.SplashActivity
import com.bankmtk.neuromemory.ui.sticker.StickerActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.item_sticker.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity<List<Sticker>?>() {

    @ExperimentalCoroutinesApi
    override val model:MainViewModel by viewModel()
    override val layoutRes: Int= R.layout.activity_main
    private lateinit var adapter: MainAdapter

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.bankmtk.neuromemory.service"
    private val description = "Test notification"
    private val vibrate = longArrayOf(0,50,100,50,50,50)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        overridePendingTransition(R.anim.slidein,R.anim.slideout)

        adapter = MainAdapter(object : MainAdapter.OnItemClickListener{
            override fun onItemLongClick(sticker: Sticker) {
                openStickerScreen(sticker)
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemClick(itemView: View) {
                if (itemView.langOneI.visibility == View.VISIBLE){
                    animateView(itemView)
                }else{
                    animateViewCancel(itemView)
                }
            }
        })
        myRecycler.adapter = adapter

        fab.setOnClickListener { openStickerScreen(null) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun renderData(data: List<Sticker>?) {
        if (data==null) return
        adapter.stickers = data
        if (isHaveItem(adapter.stickers)){
            notifyUser()
            alert_visible.setImageLevel(1)
        }else{
            alert_visible.setImageLevel(0)
        }
    }
    private fun openStickerScreen(sticker: Sticker?){
        StickerActivity.start(this,sticker?.id)
    }

     fun alertMe(view: View){
         val alertIntent = Intent(this,AlertActivity::class.java)
         if (isHaveItem(adapter.stickers)){
             startActivity(alertIntent)
         }else
         {
             val myToast = Toast.makeText(this,R.string.no_active_tasks, Toast.LENGTH_SHORT)
             myToast.setGravity(Gravity.BOTTOM, 0,200)
             val toastContainer = myToast.view as LinearLayout
             val myImage = ImageView(this)
             myImage.setImageResource(R.drawable.ic_visibility_off_black_24dp)
             toastContainer.addView(myImage,0)
             toastContainer.setBackgroundColor(Color.TRANSPARENT)
             myToast.show()
         }
    }

    companion object{
        fun getStartIntent(context: Context) = Intent(context,
        MainActivity::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.logout -> showLogoutDialog().let{true}
            else -> false
    }
    private fun showLogoutDialog(){
        alert {
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.ok_bth_title){onLogout()}
            negativeButton(R.string.logout_dialog_cancel){dialog -> dialog.dismiss()  }
        }.show()
    }
     fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener{
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.slidein,R.anim.slideout)
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
        val pendingIntent = PendingIntent.getActivity(applicationContext,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelId,description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = vibrate
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this,channelId)
                .setContentTitle("Status:")
                .setContentText("confirm items")
                .setSmallIcon(R.drawable.notify)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.notify))
                .setContentIntent(pendingIntent)
        } else{
            builder = Notification.Builder(this)
                .setContentTitle("Status:")
                .setContentText("confirm items")
                .setSmallIcon(R.drawable.notify)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.notify))
                .setContentIntent(pendingIntent)

        }
        notificationManager.notify(1234,builder.build())
    }
}



