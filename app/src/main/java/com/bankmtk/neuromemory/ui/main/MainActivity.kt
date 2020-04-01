package com.bankmtk.neuromemory.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.sticker.StickerActivity
import com.bankmtk.neuromemory.ui.sticker.StickerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_stick.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: MainAdapter
    lateinit var sViewModel: StickerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener{openStickerScreen(null)}

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        sViewModel = ViewModelProviders.of(this).get(StickerViewModel::class.java)
        adapter = MainAdapter(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(sticker: Sticker) {
                openStickerScreen(sticker)
            }
        })
        myRecycler.adapter = adapter

        viewModel.viewState().observe(this,Observer<MainViewState>{
            t ->t?.let{adapter.stickers = it.stickers}
        })
    }
    private fun openStickerScreen(sticker: Sticker?){
        val intent = StickerActivity.getStartIntent(this,sticker)
        startActivity(intent)
    }
    }
