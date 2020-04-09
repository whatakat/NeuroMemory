package com.bankmtk.neuromemory.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.base.BaseActivity
import com.bankmtk.neuromemory.ui.base.BaseViewModel
import com.bankmtk.neuromemory.ui.sticker.StickerActivity
import com.bankmtk.neuromemory.ui.sticker.StickerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_stick.*

class MainActivity : BaseActivity<List<Sticker>?, MainViewState>() {
    override val viewModel:MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)}
    override val layoutRes: Int= R.layout.activity_main
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        adapter = MainAdapter(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(sticker: Sticker) {
                openStickerScreen(sticker)
            }
        })
        myRecycler.adapter = adapter

        fab.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                openStickerScreen(null)
            }
        })
    }

    override fun renderData(data: List<Sticker>?) {
        if (data==null) return
        adapter.stickers = data
    }
    private fun openStickerScreen(sticker: Sticker?){
        val intent  = StickerActivity.getStartIntent(this, sticker)
        startActivity(intent)
    }
    companion object{
        fun getStartIntent(context: Context) = Intent(context,
        MainActivity::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.logout -> showLogoutDialog().let{true}
            else -> false
    }

}
