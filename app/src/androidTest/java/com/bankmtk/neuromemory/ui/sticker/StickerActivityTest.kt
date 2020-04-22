package com.bankmtk.neuromemory.ui.sticker

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Rule
import androidx.test.rule.ActivityTestRule
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Color
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.getColorInt
import com.google.common.base.CharMatcher.`is`
import com.google.common.base.Predicates.not
import io.mockk.*
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import java.util.regex.Pattern.matches


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

//    @Test
//    fun should_show_color_picker(){
//        onView(withId(R.id.palette)).perform(click())
//
//        onView(withId(R.id.colorPicker)).check(matches(isCompletelyDisplayed()))
//    }
//    @Test
//    fun should_hide_color_picker(){
//        onView(withId(R.id.palette)).perform(click()).perform(click())
//        onView(withId(R.id.colorPicker)).check(matches(not(isDisplayed())))
//    }
    @Test
    fun should_set_toolbar_color(){
    onView(withId(R.id.palette)).perform(click())
    //onView(withTagValue(`is`(Color.BLUE))).perform(click())

    var colorInt = Color.BLUE.getColorInt(activityTestRule.activity)

    onView(withId(R.id.toolbar)).check{view, _ ->
        assertTrue("toolbar background color does not match",
            (view.background as? ColorDrawable)?.color == colorInt)
    }
}
    @Test
    fun should_call_viewModel_loadSticker(){
        verify (exactly =1){viewModel.loadSticker(testSticker.id)}
    }




}