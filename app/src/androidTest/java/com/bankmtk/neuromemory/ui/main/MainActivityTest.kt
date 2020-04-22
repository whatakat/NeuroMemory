package com.bankmtk.neuromemory.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.intent.rule.IntentsTestRule
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.sticker.StickerActivity
import io.mockk.mockk
import org.junit.After
import org.junit.Rule
import org.koin.standalone.StandAloneContext

class MainActivityTest{

    @get:Rule
    val activityTestRule = IntentsTestRule(MainActivity::class.java, true, false)

    private val EXTRA_STICKER = StickerActivity::class.java.name+"extra.STICKER_ID"
    private val viewModel: MainViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<MainViewState>()
    private val testStickers = listOf(Sticker("333","first","langOne","langTwo"),
        Sticker("444","second","langOne1","langTwo1"),
        Sticker("555","third","langOne2","langTwo2"))
    @After
    fun tearDown(){
        StandAloneContext.stopKoin()
    }
}