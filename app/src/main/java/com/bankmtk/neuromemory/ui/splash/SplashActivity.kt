package com.bankmtk.neuromemory.ui.splash

import android.os.Handler
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.ui.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

private const val START_DELAY = 0L

class SplashActivity : BaseActivity<Boolean?>() {
    override val model: SplashViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_fragment

    @ExperimentalCoroutinesApi
    override fun onResume() {
        super.onResume()
        Handler().postDelayed({model.requestUser()}, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startStarActivity()
        }
    }
    private fun startStarActivity(){
        startActivity(StarActivity.getStartIntent(this))
        finish()
    }
}