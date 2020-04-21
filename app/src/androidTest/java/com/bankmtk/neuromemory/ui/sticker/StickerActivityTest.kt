package com.bankmtk.neuromemory.ui.sticker

import org.junit.Rule
import android.support.test.rule.ActivityTestRule

class StickerActivityTest{
    @get: Rule
    val activityTestRule = ActivityTestRule(StickerActivity::class.java, true, false)
}