package com.bankmtk.neuromemory.ui.sticker

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import org.junit.Rule
import androidx.test.rule.ActivityTestRule
import com.bankmtk.neuromemory.data.model.Sticker
import io.mockk.*
import org.junit.Before
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules


class StickerActivityTest{
    @get: Rule
    val activityTestRule = ActivityTestRule(StickerActivity::class.java, true, false)

    private val viewModel: StickerViewModel = spyk(StickerViewModel(mockk()))

    private val viewStateLiveData = MutableLiveData<StickerViewState>()

    @Before
    fun setUp(){
        loadKoinModules(listOf(
            module { viewModel{viewModel} }
        ))
        every { viewModel.getViewState() } returns viewStateLiveData
        every { viewModel.loadSticker(any()) } just  runs
        every { viewModel.saveChanges(any()) } just runs
        every { viewModel.deleteSticker() } just runs

        Intent().apply {
            putExtra(StickerActivity::class.java.name + "extra.STICKER_ID", testSticker.id)
        }.let {
            activityTestRule.launchActivity(it)
        }
    }

    private val testSticker = Sticker("333","title","langOne","langTwo")





}