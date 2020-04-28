package com.bankmtk.neuromemory.ui.main
//
//import androidx.lifecycle.MutableLiveData
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.contrib.RecyclerViewActions.*
//import androidx.test.espresso.intent.Intents.intended
//import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
//import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
//import androidx.test.espresso.intent.rule.IntentsTestRule
//import androidx.test.espresso.matcher.ViewMatchers.*
//import com.bankmtk.neuromemory.R
//import com.bankmtk.neuromemory.data.model.Sticker
//import com.bankmtk.neuromemory.ui.sticker.StickerActivity
//import com.bankmtk.neuromemory.ui.sticker.StickerViewModel
//import io.mockk.every
//import io.mockk.mockk
//import org.hamcrest.Matchers.allOf
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.koin.android.viewmodel.ext.koin.viewModel
//import org.koin.dsl.module.module
//import org.koin.standalone.StandAloneContext
//
//class MainActivityTest{
//
//    @get:Rule
//    val activityTestRule = IntentsTestRule(MainActivity::class.java, true, false)
//
//    private val EXTRA_STICKER = StickerActivity::class.java.name+"extra.STICKER_ID"
//    private val viewModel: MainViewModel = mockk(relaxed = true)
//    private val viewStateLiveData = MutableLiveData<MainViewState>()
//    private val testStickers = listOf(Sticker("333","first","langOne","langTwo"),
//        Sticker("444","second","langOne1","langTwo1"),
//        Sticker("555","third","langOne2","langTwo2"))
//    @Before
//    fun setUp(){
//        StandAloneContext.loadKoinModules(listOf(
//            module {
//                viewModel{viewModel}
//                viewModel{ mockk<StickerViewModel>(relaxed = true)}
//            }
//        ))
//        every { viewModel.getViewState() } returns viewStateLiveData
//
//        activityTestRule.launchActivity(null)
//        viewStateLiveData.postValue(MainViewState(stickers = testStickers))
//    }
//
//    @After
//    fun tearDown(){
//        StandAloneContext.stopKoin()
//    }
//    @Test
//    fun check_data_is_displayed(){
//        onView(withId(R.id.myRecycler))
//            .perform(scrollToPosition<MainAdapter.StickViewHolder>(1))
//        onView(withText(testStickers[1].langOne)).check(matches(isDisplayed()))
//    }
//    @Test
//    fun check_detail_activity_intent_sent(){
//        onView(withId(R.id.myRecycler))
//            .perform(actionOnItemAtPosition<MainAdapter.StickViewHolder>(1, click()))
//        intended(allOf(hasComponent(StickerActivity::class.java.name),
//        hasExtra(EXTRA_STICKER,testStickers[1].id)))
//    }
//}