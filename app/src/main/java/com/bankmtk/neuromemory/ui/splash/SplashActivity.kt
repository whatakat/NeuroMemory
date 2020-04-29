package com.bankmtk.neuromemory.ui.splash

import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.ui.base.BaseActivity
import com.bankmtk.neuromemory.ui.main.MainActivity
import com.bankmtk.neuromemory.ui.splash.StarFragment.Companion.newInstance
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

private const val START_DELAY = 2000L

class SplashActivity : BaseActivity<Boolean?>() {
    override val model: SplashViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_splash
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