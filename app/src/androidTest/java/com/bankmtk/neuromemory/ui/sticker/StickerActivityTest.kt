package com.bankmtk.neuromemory.ui.sticker

import org.junit.Rule
import androidx.test.rule.ActivityTestRule
import io.mockk.mockk
import io.mockk.spyk

class StickerActivityTest{
    @get: Rule
    val activityTestRule = ActivityTestRule(StickerActivity::class.java, true, false)

    private val viewModel: StickerViewModel = spyk(StickerViewModel(mockk()))
}