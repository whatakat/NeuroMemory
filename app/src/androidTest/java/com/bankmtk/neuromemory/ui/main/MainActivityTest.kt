package com.bankmtk.neuromemory.ui.main

import androidx.test.espresso.intent.rule.IntentsTestRule
import org.junit.Assert.*
import org.junit.Rule

class MainActivityTest{

    @get:Rule
    val activityTestRule = IntentsTestRule(MainActivity::class.java, true, false)
}