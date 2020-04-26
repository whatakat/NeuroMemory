package com.bankmtk.neuromemory.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<S>: ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Default + Job()
    }

}