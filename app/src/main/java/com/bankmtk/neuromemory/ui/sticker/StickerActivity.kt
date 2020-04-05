package com.bankmtk.neuromemory.ui.sticker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Color
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.DATE_TIME_FORMAT
import com.bankmtk.neuromemory.extentions.format
import com.bankmtk.neuromemory.extentions.getColorInt
import com.bankmtk.neuromemory.ui.base.BaseActivity
import com.bankmtk.neuromemory.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_stick.*
import kotlinx.android.synthetic.main.item_sticker.*
import java.text.SimpleDateFormat
import java.util.*

private const val SAVE_DELAY = 2000L

class StickerActivity: BaseActivity<Sticker?, StickerViewState>() {
    override val viewModel: StickerViewModel by lazy {
        ViewModelProviders.of(this).get(StickerViewModel::class.java)}
    override val layoutRes: Int = R.layout.activity_stick
    private var sticker: Sticker? = null



    companion object{
        private val EXTRA_STICKER = StickerActivity::class.java.name+"extra.STICKER"
        private lateinit var viewModel: StickerViewModel

        fun getStartIntent(context: Context, sticker: Sticker?):Intent{
            val intent = Intent(context, StickerActivity::class.java)
            intent.putExtra(EXTRA_STICKER, sticker)
            return intent
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stickerId = intent.getStringExtra(EXTRA_STICKER)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (stickerId != null){
        stickerId.let {
            viewModel.loadSticker(it)
        }}
        else if (stickerId == null) {
            supportActionBar?.title =
                getString(R.string.new_sticker_title)
            titleEt.addTextChangedListener(textChangeListener)
            textOne.addTextChangedListener(textChangeListener)
            textTwo.addTextChangedListener(textChangeListener)
        }
    }

    private fun initView() {
        sticker?.run {
            supportActionBar?.title = lastChanged.format()

            titleEt.setText(title)
            textOne.setText(langOne)
            textTwo.setText(langTwo)

            toolbar.setBackgroundColor(color.getColorInt(this@StickerActivity))
        }
    }

    override fun renderData(data: Sticker?) {
        this.sticker = data
        initView()
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
        if (titleEt.text!!.length<3)return //I have one question
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