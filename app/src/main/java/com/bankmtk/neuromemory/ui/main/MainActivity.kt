package com.bankmtk.neuromemory.ui.main


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.*
import com.bankmtk.neuromemory.ui.alert.AlertActivity
import com.bankmtk.neuromemory.ui.base.BaseActivity
import com.bankmtk.neuromemory.ui.splash.SplashActivity
import com.bankmtk.neuromemory.ui.sticker.StickerActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_sticker.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : BaseActivity<List<Sticker>?>(), TextToSpeech.OnInitListener {

    @ExperimentalCoroutinesApi
    override val model:MainViewModel by viewModel()
    override val layoutRes: Int= R.layout.activity_main
    private lateinit var adapter: MainAdapter

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.bankmtk.neuromemory.service"
    private val description = "Notification"
    private var isRotate: Boolean = false
    private var listTitleName:Set<String> = setOf()
    private var myTTS: TextToSpeech? = null
    private val bundle = Bundle()
    private var st:Sticker?=null
    private var statusSp:Boolean = false
    private var animationDrawable: AnimationDrawable? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        overridePendingTransition(R.anim.slidein, R.anim.slideout)
        init(fab)
        init(alert_button)

        val imageView = findViewById<ImageView>(R.id.title_background)
        imageView.setBackgroundResource(R.drawable.title_animation)

        animationDrawable = imageView.background as AnimationDrawable


        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
            override fun onItemLongClick(sticker: Sticker) {
                openStickerScreen(sticker)
            }

            override fun onItemSpeakClick(sticker: Sticker) {
                when (statusSp) {
                    false -> {
                        st = sticker
                        statusSp = true
                        val checkIntent = Intent()
                        checkIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
                        startActivityForResult(checkIntent, 1)
                        animationDrawable?.start()
                    }
                    true -> {
                        myTTS!!.stop()
                        myTTS!!.shutdown()
                        statusSp = false
                        animationDrawable?.stop()
                    }
                }

            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemClick(itemView: View) {
                if (itemView.langOneI.visibility == View.VISIBLE) {
                    animateView(itemView)
                } else {
                    animateViewCancel(itemView)
                }
            }
        })
        myRecycler.adapter = adapter

        fab.setOnClickListener { openStickerScreen(null) }
        main_button!!.setOnClickListener { v->
            isRotate = rotateFab(v, !isRotate)
            if (isRotate) {
                showIn(fab)
                showIn(alert_button)
            } else {
                showOut(fab)
                showOut(alert_button)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun renderData(data: List<Sticker>?) {
        if (data==null) return
        listTitleName =data.map { item->item.title }.toSortedSet()
        adapter.stickers = data
        if (isHaveItem(adapter.stickers)){
            notifyUser()
            alert_visible.setImageLevel(1)
        }else{
            alert_visible.setImageLevel(0)
        }
    }
    private fun openStickerScreen(sticker: Sticker?){
        StickerActivity.start(this, sticker?.id)
    }

     fun alertMe(view: View){
         val alertIntent = Intent(this, AlertActivity::class.java)
         if (isHaveItem(adapter.stickers)){
             startActivity(alertIntent)
         }else
         {
             val myToast = Toast.makeText(this, R.string.no_active_tasks, Toast.LENGTH_SHORT)
             myToast.setGravity(Gravity.BOTTOM, 0, 200)
             val toastContainer = myToast.view as LinearLayout
             val myImage = ImageView(this)
             myImage.setImageResource(R.drawable.ic_visibility_off_black_24dp)
             toastContainer.addView(myImage, 0)
             toastContainer.setBackgroundColor(Color.TRANSPARENT)
             myToast.show()
         }
    }


    companion object{
        fun getStartIntent(context: Context) = Intent(
            context,
            MainActivity::class.java
        )
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        listTitleName.map { tit->if(!menu!!.isContains(tit)) menu.add(tit) }.let { true }

        return super.onPrepareOptionsMenu(menu)
    }

    @ExperimentalCoroutinesApi
    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        onStart()
        return super.onMenuOpened(featureId, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.title.toString()){
            "Logout" -> showLogoutDialog().let { true }
             else->  updateSearch(item.title.toString(), adapter.stickers).let { true }

        }
    }


private fun updateSearch(selectedTitle: String?, data: List<Sticker>?) {

    if (selectedTitle == "All items") {
        if (data != null) {
            adapter.stickers = data
        }
    } else {
        adapter.stickers = data?.filter {
            it.title==selectedTitle
        } as List
    }
    adapter.notifyDataSetChanged()
}

    private fun showLogoutDialog(){
        alert {
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.ok_bth_title){onLogout()}
            negativeButton(R.string.logout_dialog_cancel){ dialog -> dialog.dismiss()  }
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
        overridePendingTransition(R.anim.slidein, R.anim.slideout)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (myTTS!=null){
            myTTS!!.stop()
            myTTS!!.shutdown()
            statusSp = false
        }
    }

    override fun animateView(view: View) {
        view.fabVolume.hide()
        super.animateView(view)
    }

    override fun animateViewCancel(view: View) {
        view.fabVolume.animate().alpha(0.07F)
        view.fabVolume.show()
        super.animateViewCancel(view)
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
                .setContentTitle("Status:")
                .setContentText("confirm items")
                .setSmallIcon(R.drawable.notify_back)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.notify_back))
                .setContentIntent(pendingIntent)
        } else{
            builder = Notification.Builder(this)
                .setContentTitle("Status:")
                .setContentText("confirm items")
                .setSmallIcon(R.drawable.notify_back)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.notify_back))
                .setContentIntent(pendingIntent)

        }
        notificationManager.notify(1234, builder.build())
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                myTTS = TextToSpeech(this, this)
                myTTS!!.language = Locale.ROOT
            } else {
                val ttsLoadIntent = Intent()
                ttsLoadIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(ttsLoadIntent)
            }
        }

    }

    override fun onInit(status: Int) {
        bundle.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "speakText")
        if (status == TextToSpeech.SUCCESS) {
            myTTS!!.speak(st!!.langTwo, TextToSpeech.QUEUE_FLUSH, bundle, "speakText")
        } else if (status == TextToSpeech.ERROR) {
            myTTS!!.shutdown()
        }
    }

}
