package com.bankmtk.neuromemory.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.sticker.StickerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        adapter = MainAdapter()
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
