package com.bankmtk.neuromemory.ui.main

import androidx.test.espresso.intent.rule.IntentsTestRule
import com.bankmtk.neuromemory.ui.sticker.StickerActivity
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Rule

class MainActivityTest{

    @get:Rule
    val activityTestRule = IntentsTestRule(MainActivity::class.java, true, false)

    private val EXTRA_STICKER = StickerActivity::class.java.name+"extra.STICKER_ID"
    private val viewModel: MainViewModel = mockk(relaxed = true)

}