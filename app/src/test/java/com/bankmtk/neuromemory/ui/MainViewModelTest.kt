package com.bankmtk.neuromemory.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.ui.main.MainViewModel
import com.nhaarman.mockitokotlin2.mock

import io.mockk.MockK
import io.mockk.every
import org.junit.Before

import org.junit.Rule

class MainViewModelTest {
    @get: Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepositoty: Repository = mock<Repository>()
    private val stickerLivedata = MutableLiveData<Result>()
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp(){
        every { mockRepositoty.getStickers() } returns stickerLivedata
        viewModel = MainViewModel(mockRepositoty)
    }
}