package com.bankmtk.neuromemory.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bankmtk.neuromemory.data.Repository
import com.nhaarman.mockitokotlin2.mock

import io.mockk.MockK

import org.junit.Rule

class MainViewModelTest {
    @get: Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepositoty: Repository = mock<Repository>()
}