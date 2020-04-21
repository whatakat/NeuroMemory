package com.bankmtk.neuromemory.ui.sticker

import androidx.lifecycle.MutableLiveData
import org.junit.Rule
import androidx.test.rule.ActivityTestRule
import com.bankmtk.neuromemory.data.model.Sticker
import io.mockk.*


class StickerActivityTest{
    @get: Rule
    val activityTestRule = ActivityTestRule(StickerActivity::class.java, true, false)

    private val viewModel: StickerViewModel = spyk(StickerViewModel(mockk()))

    private val viewStateLiveData = MutableLiveData<StickerViewState>()

    fun setUp(){
        every { viewModel.getViewState() } returns viewStateLiveData
        every { viewModel.loadSticker(any()) } just  runs
        every { viewModel.saveChanges(any()) } just runs
        every { viewModel.deleteSticker() } just runs
    }

    private val testSticker = Sticker("333","title","langOne","langTwo")




}