package com.bankmtk.neuromemory.ui.sticker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
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
import com.bankmtk.neuromemory.ui.splash.SplashViewModel
import kotlinx.android.synthetic.main.activity_stick.*
import kotlinx.android.synthetic.main.item_sticker.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

private const val SAVE_DELAY = 2000L

class StickerActivity: BaseActivity<StickerViewState.Data, StickerViewState>() {
    override val model: StickerViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_stick
    private var sticker: Sticker? = null
    private var color: Color = Color.WHITE



    companion object{
        private val EXTRA_STICKER = StickerActivity::class.java.name+"extra.STICKER"
        fun start(context: Context, stickerId: String?)=
            context.startActivity<StickerActivity>(EXTRA_STICKER to stickerId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val stickerId = intent.getStringExtra(EXTRA_STICKER)
        if (stickerId != null){
        stickerId.let {
            model.loadSticker(it)
        }}
        else if (stickerId == null) {
            supportActionBar?.title =
                getString(R.string.new_sticker_title)
            titleEt.addTextChangedListener(textChangeListener)
            textOne.addTextChangedListener(textChangeListener)
            textTwo.addTextChangedListener(textChangeListener)
        }
        colorPicker.onColorClickListener = {
            color = it
            setToolbarColor(it)
            triggerSaveSticker()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        menuInflater.inflate(R.menu.sticker_menu, menu).let { true}


    private fun initView() {
        sticker?.run {
            supportActionBar?.title = lastChanged.format()
            toolbar.setBackgroundColor(color.getColorInt(this@StickerActivity)) //? only this

            removeEditListener()
            titleEt.setText(title)
            textOne.setText(langOne)
            textTwo.setText(langTwo)
            setEditListener()

        }
    }

    override fun renderData(data: StickerViewState.Data) {
        if (data.isDeleted) finish()

        this.sticker = data.sticker
        data.sticker?.let { color = it.color }
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
        android.R.id.home ->super.onBackPressed().let { true }
            R.id.palette -> togglePalette().let {true}
            R.id.delete -> deleteSticker().let {true}
            else -> super.onOptionsItemSelected(item)
    }
    private fun togglePalette(){
            if (colorPicker.isOpen){
                colorPicker.close()
            }else{
                colorPicker.open()
            }
        }

    override fun onBackPressed() {
        if (colorPicker.isOpen){
            colorPicker.close()
            return
        }
        super.onBackPressed()
    }

    private fun deleteSticker(){
        alert {
            messageResource = R.string.delete_dialog_message
            negativeButton(R.string.cancel_btn_title){dialog -> dialog.dismiss() }
            positiveButton(R.string.ok_bth_title){model.deleteSticker()}
        }.show()
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
        if (titleEt.text!!.length<3 && langOne.text.length<3 && langTwo.text.length<3)return //I have one question
        Handler().postDelayed({
            sticker = sticker?.copy(title = titleEt.text.toString(),
            langOne = langOne.text.toString(),
            langTwo = langTwo.text.toString(),
            lastChanged = Date(),
            color = color)
                ?:createNewSticker()
            sticker?.let { model.saveChanges(it) }
        }, SAVE_DELAY)
    }
    private fun setToolbarColor(color: Color){
        toolbar.setBackgroundColor(color.getColorInt(this))
    }
    private fun createNewSticker(): Sticker = Sticker(UUID.randomUUID().toString(),
    titleEt.text.toString(), textOne.text.toString(),textTwo.text.toString())

    private fun setEditListener(){
        titleEt.addTextChangedListener(textChangeListener)
        textOne.addTextChangedListener(textChangeListener)
        textTwo.addTextChangedListener(textChangeListener)
    }
    private fun  removeEditListener(){
        titleEt.removeTextChangedListener(textChangeListener)
        textOne.removeTextChangedListener(textChangeListener)
        textTwo.removeTextChangedListener(textChangeListener)

    }

}