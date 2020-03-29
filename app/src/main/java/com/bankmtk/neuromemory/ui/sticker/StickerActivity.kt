package com.bankmtk.neuromemory.ui.sticker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import kotlinx.android.synthetic.main.activity_stick.*
import java.text.SimpleDateFormat
import java.util.*

class StickerActivity: AppCompatActivity() {
    companion object{
        private val EXTRA_STICKER = StickerActivity::class.java.name+"extra.STICKER"

        fun getStartIntent(context: Context, sticker: Sticker?):Intent{
            val intent = Intent(context, StickerActivity::class.java)
            intent.putExtra(EXTRA_STICKER, sticker)
            return intent
        }
    }
    private var sticker: Sticker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stick)

        sticker = intent.getParcelableExtra(EXTRA_STICKER)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = if (sticker != null){
            SimpleDateFormat(DATE_TIME_FORMAT,
            Locale.getDefault()).format(sticker!!.lastChanged)
        }else{
            getString(R.string.new_sticker_title)
        }
        initView()
    }
}