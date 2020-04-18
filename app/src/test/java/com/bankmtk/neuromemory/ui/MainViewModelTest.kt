package com.bankmtk.neuromemory.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.main.MainViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify

import io.mockk.MockK
import io.mockk.every
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before

import org.junit.Rule
import org.junit.Test

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

    @Test
    fun `should call getStickers once` (){
        verify(exactly = 1){mockRepositoty.getStickers()}
    }
    @Test
    fun `should return error`(){
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.getViewState().observeForever{result = it?.error}
        stickerLivedata.value = Result.Error(testData)
        assertEquals(result,testData)

    }
    @Test
    fun `should return Stickers`(){
        var result:List<Sticker>? = null
        val testData = listOf(Sticker(id = "1"), Sticker(id = "2"))
        viewModel.getViewState().observeForever{result = it?.data}
        stickerLivedata.value = Result.Success(testData)
        assertEquals(testData,result)
    }
}