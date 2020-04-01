package com.bankmtk.neuromemory.ui.sticker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Color
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.DATE_TIME_FORMAT
import kotlinx.android.synthetic.main.activity_stick.*
import kotlinx.android.synthetic.main.item_sticker.*
import java.text.SimpleDateFormat
import java.util.*

private const val SAVE_DELAY = 2000L

class StickerActivity: AppCompatActivity() {
    companion object{
        private val EXTRA_STICKER = StickerActivity::class.java.name+"extra.STICKER"
        private lateinit var viewModel: StickerViewModel

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
    private fun initView(){
        if (sticker != null){
            titleEt.setText(sticker?.title ?:"")
            textOne.setText(sticker?.langOne ?:"")
            textTwo.setText(sticker?.langTwo ?:"")
            val color = when(sticker!!.color){
                Color.WHITE ->R.color.white
                Color.VIOLET ->R.color.violet
                Color.BLUE ->R.color.blue
                Color.GREEN ->R.color.green
                Color.PINK ->R.color.pink
                Color.RED ->R.color.red
                Color.YELLOW ->R.color.yellow
            }
            toolbar.setBackgroundColor(resources.getColor(color))
        }
        titleEt.addTextChangedListener(textChangeListener)
        textOne.addTextChangedListener(textChangeListener)
        textTwo.addTextChangedListener(textChangeListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
        android.R.id.home ->{
            onBackPressed()
            true
        }
            else -> super.onOptionsItemSelected(item)
    }
    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            triggerSaveSticker()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //
        }
    }
    private fun triggerSaveSticker(){
        //if (title.text.length<3)return
        Handler().postDelayed(object : Runnable{
            override fun run() {
                sticker = sticker?.copy(title = titleEt.text.toString(),
                langOne= textOne.text.toString(),
                langTwo = textTwo.text.toString(),
                lastChanged = Date())
                    ?:createNewSticker()

                if (sticker != null) viewModel.saveChanges(sticker!!)
            }
        }, SAVE_DELAY)
    }
    private fun createNewSticker(): Sticker = Sticker(UUID.randomUUID().toString(),
    titleEt.text.toString(), textOne.text.toString(),textTwo.text.toString())
}