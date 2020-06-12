package com.bankmtk.neuromemory.ui.alert

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.format
import com.bankmtk.neuromemory.ui.base.BaseActivity
import com.bankmtk.neuromemory.ui.main.MainViewModel
import com.bankmtk.neuromemory.ui.sticker.StickerViewModel
import kotlinx.android.synthetic.main.activity_main.myRecycler
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.item_sticker.*
import kotlinx.android.synthetic.main.item_sticker.view.*
import kotlinx.android.synthetic.main.item_sticker.view.fabOk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AlertActivity:BaseActivity<List<Sticker>?>() {

    @ExperimentalCoroutinesApi
    override val model: MainViewModel by viewModel()
    private val modelS: StickerViewModel by viewModel()
    override val layoutRes: Int= R.layout.activity_alert
    private lateinit var adapter: AlertAdapter

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        overridePendingTransition(R.anim.alert_slidein,R.anim.alert_slideout)


        adapter = AlertAdapter(object : AlertAdapter.OnItemClickListener{

            override fun onItemLongClick(sticker: Sticker) {
                stickerOk(sticker)
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
    }

    override fun renderData(data: List<Sticker>?) {
        if (data==null) return
        adapter.stickers = data
    }

    companion object{
        fun getStartIntent(context: Context) = Intent(context,
            AlertActivity::class.java)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.alert_slidein,R.anim.alert_slideout)
    }

    override fun animateView(view: View) {
        super.animateView(view)
        view.fabOk.alpha = 0.2F
        view.fabOk.animate().alpha(0.7F)
        view.fabOk.show()
        view.fabOk.rotationY = 180F
    }

    override fun animateViewCancel(view: View) {
        super.animateViewCancel(view)
        view.fabOk.hide()
        view.fabOk.animate().alpha(0.2F)
    }
    @ExperimentalCoroutinesApi
    private fun stickerOk(sticker: Sticker?){
        launch {
            sticker?.lastChanged =nextChange(sticker?.progressSt)
            sticker?.progressSt = nextLevel(sticker?.progressSt)
            sticker?.let { modelS.saveChanges(it) }
            finish()
        }
        toast("Next date ${nextChange(sticker!!.progressSt).format()}")
        if (isHaveItem(adapter.stickers)){
            startActivity(this.intent)
        }else {
            val myToast = Toast.makeText(this,R.string.time_completed, Toast.LENGTH_SHORT)
            myToast.setGravity(Gravity.CENTER, 0,200)
            val toastContainer = myToast.view as LinearLayout
            val myImage = ImageView(this)
            myImage.setImageResource(R.drawable.ic_verified_user_black_24dp)
            toastContainer.addView(myImage,0)
            toastContainer.setBackgroundColor(ContextCompat.getColor(this,R.color.night_sky))
            //toastContainer.setBackgroundColor(Color.TRANSPARENT)
            myToast.show()
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slidein,R.anim.slideout)
    }
    private fun nextChange(progressSt:Int?):Date{
        var nextDate = Date()
        when(progressSt){
            0->nextDate = Date(Date().time.plus(10*60*1000))
            1->nextDate = Date(Date().time.plus(60*60*1000))
            2->nextDate = Date(Date().time.plus(5*60*60*1000))
            3->nextDate = Date(Date().time.plus(24*60*60*1000))
            4->nextDate = Date(Date().time.plus(5*24*60*60*1000))
            5->nextDate = Date(Date().time.plus(25*24*60*60*1000L))
            6->nextDate = Date(Date().time.plus(4*30*24*60*60*1000L))
            7,8,9->nextDate = Date(Date().time.plus(12*30*24*60*60*1000L))
        }
        return nextDate
    }
    private fun nextLevel(progressSt: Int?):Int{
        var itemLevel = 0
        when(progressSt){
            0->itemLevel =1
            1->itemLevel =2
            2->itemLevel =3
            3->itemLevel =4
            4->itemLevel =5
            5->itemLevel =6
            6->itemLevel =7
            7->itemLevel =8
            8->itemLevel =9
            9->itemLevel =9
        }
        return itemLevel
    }
    private fun isHaveItem(data: List<Sticker>?):Boolean {
        for(i in data!!.indices){
            if (data[i].lastChanged< Date()&& data.size>1) {
                return true
            }
        }
        return false
    }
}