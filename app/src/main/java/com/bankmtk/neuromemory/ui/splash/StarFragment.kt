package com.bankmtk.neuromemory.ui.splash

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import com.bankmtk.neuromemory.R

class StarFragment: Fragment() {
    private var mSceneView: View? = null
    private var mSunView: View? = null
    private var mSkyView: View? = null
    private var mBlackSkyColor = 0
    private var mSunsetSkyColor = 0
    private var mNightSkyColor = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_splash, container, false)
        mSceneView = view
        mSunView = view.findViewById(R.id.starCenter)
        mSkyView = view.findViewById(R.id.sky)
        val resources = resources
        mBlackSkyColor = resources.getColor(R.color.black)
        mSunsetSkyColor = resources.getColor(R.color.night_sky)
        mNightSkyColor = resources.getColor(R.color.night_sky)
        mSceneView!!.setOnClickListener { startAnimation() }
        return view
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun startAnimation() {
        val sunYStart = mSunView!!.top.toFloat()
        val sunYEnd = mSkyView!!.height.toFloat()
        val heightAnimator = ObjectAnimator
            .ofFloat(mSunView, "y", sunYStart, sunYEnd)
            .setDuration(2000)
        heightAnimator.interpolator = AccelerateInterpolator(2F)
        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor", mBlackSkyColor, mSunsetSkyColor)
            .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())
        val nightSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor",  mNightSkyColor, mBlackSkyColor)
            .setDuration(3000)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())
        val animatorSet = AnimatorSet()
        animatorSet
            .play(heightAnimator)
            .with(sunsetSkyAnimator)
            .before(nightSkyAnimator)
        animatorSet.start()
    }


    companion object {
        @JvmStatic
        fun newInstance(): StarFragment {
            return StarFragment()
        }
    }
}