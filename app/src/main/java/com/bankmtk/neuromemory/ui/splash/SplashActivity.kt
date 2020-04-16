package com.bankmtk.neuromemory.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.ui.base.BaseActivity
import com.bankmtk.neuromemory.ui.main.MainActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

private const val START_DELAY = 2000L

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {
    override val model:SplashViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_splash //activity_splash
    override fun onResume() {
        super.onResume()
        Handler().postDelayed({model.requestUser()}, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }
    private fun startMainActivity(){
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}