package com.bankmtk.neuromemory.ui.sticker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Color
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.format
import com.bankmtk.neuromemory.extentions.getColorInt
import com.bankmtk.neuromemory.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_stick.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class StickerActivity: BaseActivity<StickerViewState.StickerData>() {
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
            saveSticker()
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

    override fun renderData(data: StickerViewState.StickerData) {
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
        saveSticker()
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
            //saveSticker()

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }
    private fun saveSticker(){
        if (titleEt.text == null || (titleEt.text?.length ?: 0)<3) return
        launch {
            sticker = sticker?.copy(
                title = titleEt.text.toString(),
                langOne = textOne.text.toString(),
                langTwo = textTwo.text.toString(),
                lastChanged = Date(),
                color = color)
                ?: createNewSticker()

            sticker?.let { model.saveChanges(it) }
        }
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