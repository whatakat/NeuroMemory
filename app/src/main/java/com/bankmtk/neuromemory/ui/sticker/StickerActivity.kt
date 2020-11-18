package com.bankmtk.neuromemory.ui.sticker

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Color
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.format
import com.bankmtk.neuromemory.extentions.getColorInt
import com.bankmtk.neuromemory.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_stick.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        private const val RECOGNIZER_RESULT = 3234
    }

    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
            overridePendingTransition(R.anim.sticker_zoom_in,R.anim.sticker_zoom_out)
        speech.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text")
            try {
                startActivityForResult(intent, RECOGNIZER_RESULT)
            } catch (e: ActivityNotFoundException){
                val myToast = Toast.makeText(this,R.string.error_speech, Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.CENTER, 0,200)
                val toastContainer = myToast.view as LinearLayout
                val myImage = ImageView(this)
                myImage.setImageResource(R.drawable.ic_mic_off_black_24dp)
                toastContainer.addView(myImage, 0)
                toastContainer.setBackgroundColor(ContextCompat.getColor(this,R.color.night_sky))
                myToast.show()
            }

        }

        val stickerId = intent.getStringExtra(EXTRA_STICKER)
        if (stickerId != null){
            stickerId.let {
                model.loadSticker(it)
            }
        }
        else {
            supportActionBar?.title =
                getString(R.string.new_sticker_title)
            titleEt.addTextChangedListener(textChangeListener)
            textOne.addTextChangedListener(textChangeListener)
            textTwo.addTextChangedListener(textChangeListener)
        }
        colorPicker.onColorClickListener = {
            saveSticker()
            color = it
            setToolbarColor(it)
            setTextOneColor(it)
        }
        save_sticker.setOnClickListener { saveSticker() }
    }
    private val stickerInfo: String
        get() {
            return getString(R.string.share_stick, sticker!!.title, sticker!!.langOne, sticker!!.langTwo)
        }

    @ExperimentalStdlibApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOGNIZER_RESULT && resultCode == Activity.RESULT_OK) {
            fun checkedField():Int{
                when(true){
                    rbt.isChecked->return R.id.titleEt
                    rbf.isChecked->return R.id.textOne
                }
                return R.id.textTwo
            }
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val speechText = findViewById<View>(checkedField()) as TextView
            speechText.text =speechText.text.toString().plus(matches?.get(0).toString().toLowerCase(Locale.ROOT).capitalize(Locale.ROOT).plus(". "))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        menuInflater.inflate(R.menu.sticker_menu, menu).let { true}


    private fun initView() {
        sticker?.run {
            supportActionBar?.title = lastChanged.format()
            toolbar.setBackgroundColor(color.getColorInt(this@StickerActivity)) //? only this
            textOne.setBackgroundColor(color.getColorInt(this@StickerActivity))
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

    @ExperimentalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            android.R.id.home ->super.onBackPressed().let { true }
            R.id.palette -> togglePalette().let {true}
            R.id.delete -> deleteSticker().let {true}
            R.id.share_info-> shareStick().let {true}
            else -> super.onOptionsItemSelected(item)
        }
    private fun togglePalette(){
        if (colorPicker.isOpen){
            colorPicker.close()
        }else{
            colorPicker.open()
        }
    }
    private fun shareStick(){
      try {
          var i =Intent(Intent.ACTION_SEND)
          i.type = "text/plain"
          i.putExtra(Intent.EXTRA_TEXT,stickerInfo)
          i = Intent.createChooser(i, getString(R.string.report))
          startActivity(i)
      } catch (e: Exception){
          val myToast = Toast.makeText(this,R.string.error, Toast.LENGTH_SHORT)
          myToast.setGravity(Gravity.CENTER, 0,200)
          val toastContainer = myToast.view as LinearLayout
          val myImage = ImageView(this)
          myImage.setImageResource(R.drawable.ic_error_outline_black_24dp)
          toastContainer.addView(myImage,0)
          toastContainer.setBackgroundColor(ContextCompat.getColor(this,R.color.night_sky))
          myToast.show()
      }

    }

    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBackPressed() {
        if (colorPicker.isOpen){
            colorPicker.close()
            return
        }
        super.onBackPressed()

    }

    @ExperimentalCoroutinesApi
    private fun deleteSticker(){
        alert {
            messageResource = R.string.delete_dialog_message
            negativeButton(R.string.cancel_btn_title){dialog -> dialog.dismiss() }
            positiveButton(R.string.ok_bth_title){model.deleteSticker()}
        }.show()
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }
    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveSticker(){
        if (titleEt.text == null || (titleEt.text?.length ?: 0)<3) {
            val myToast = Toast.makeText(this,R.string.error_save, Toast.LENGTH_SHORT)
            myToast.setGravity(Gravity.CENTER, 0,200)
            val toastContainer = myToast.view as LinearLayout
            val myImage = ImageView(this)
            myImage.setImageResource(R.drawable.ic_error_outline_black_24dp)
            toastContainer.addView(myImage,0)
            toastContainer.setBackgroundColor(ContextCompat.getColor(this,R.color.night_sky))
            myToast.show()
            return
        }
        launch {
            sticker = sticker?.copy(
                title = titleEt.text.toString(),
                langOne = textOne.text.toString(),
                langTwo = textTwo.text.toString(),
                color = color)
                ?: createNewSticker()
            sticker?.let { model.saveChanges(it) }
        }
    }

    private fun setToolbarColor(color: Color){
        toolbar.setBackgroundColor(color.getColorInt(this))
    }
    private fun setTextOneColor(color: Color){
        textOne.setBackgroundColor(color.getColorInt(this))
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

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.sticker_zoom_in,R.anim.sticker_zoom_out)
    }
}