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
import kotlinx.android.synthetic.main.activity_alert.*
import kotlinx.android.synthetic.main.activity_main.myRecycler
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.item_sticker.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class AlertActivity:BaseActivity<List<Sticker>?>() {

    @ExperimentalCoroutinesApi
    override val model: MainViewModel by viewModel()
    override val layoutRes: Int= R.layout.activity_alert
    private lateinit var adapter: AlertAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        overridePendingTransition(R.anim.slidein, R.anim.slideout)


        adapter = AlertAdapter(object : AlertAdapter.OnItemClickListener{

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

        fabOk.setOnClickListener { deleteStickerScreen(null) }
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
        overridePendingTransition(R.anim.slideout, R.anim.slidein)
    }
}