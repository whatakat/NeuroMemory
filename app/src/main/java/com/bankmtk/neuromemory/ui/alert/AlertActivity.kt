package com.bankmtk.neuromemory.ui.alert

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.base.BaseActivity
import com.bankmtk.neuromemory.ui.main.MainViewModel
import com.bankmtk.neuromemory.ui.sticker.StickerViewModel
import kotlinx.android.synthetic.main.activity_main.myRecycler
import kotlinx.android.synthetic.main.activity_main.toolbar
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
    val modelS: StickerViewModel by viewModel()
    override val layoutRes: Int= R.layout.activity_alert
    private lateinit var adapter: AlertAdapter

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
    private fun deleteStickerScreen(sticker: Sticker?){
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
        view.fabOk.visibility = View.VISIBLE
        view.fabOk.rotationY = 180F
    }

    override fun animateViewCancel(view: View) {
        super.animateViewCancel(view)
        view.fabOk.visibility = View.INVISIBLE
    }
    private fun stickerOk(sticker: Sticker?){
        val date = Date(Date().time.plus(60*60*1000))
        launch {
            sticker?.lastChanged =date
        }
        sticker?.let { modelS.saveChanges(it) }
        toast("OK, next date ${date}")
    }
}