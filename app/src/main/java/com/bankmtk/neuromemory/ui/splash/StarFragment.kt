package com.bankmtk.neuromemory.ui.splash

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.Fragment
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.ui.main.MainActivity

class StarFragment: Fragment() {
    private var mButton: ImageButton? =null
    private var mSceneView: View? = null
    private var mSunView: View? = null
    private var mSkyView: View? = null
    private var mTrackView: View? = null
    private var mStarView: View? = null
    private var mStarsView: View? =null
    private var mTreeView: View? =null
    private var mMarsView: View? =null
    private var mBlackSkyColor = 0
    private var mSunsetSkyColor = 0
    private var mStarColor = 0
    private var mBlueSky = 0
    private var mWhiteColor = 0
    private var mRedColor = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_splash, container, false)
        mSceneView = view
        mSunView = view.findViewById(R.id.starCenter)
        mSkyView = view.findViewById(R.id.sky)
        mTrackView = view.findViewById(R.id.trackCenter)
        mStarView = view.findViewById(R.id.star)
        mStarsView = view.findViewById(R.id.stars)
        mTreeView = view.findViewById(R.id.tree)
        mMarsView = view.findViewById(R.id.mars)
        mBlackSkyColor = getColor(resources,R.color.black,null)
        mSunsetSkyColor = getColor(resources,R.color.night_sky, null)
        mStarColor = getColor(resources,R.color.yellow,null)
        mBlueSky = getColor(resources,R.color.blue_sky,null)
        mWhiteColor = getColor(resources,R.color.white,null)
        mRedColor = getColor(resources,R.color.red, null)
        mSceneView!!.setOnClickListener { startAnimation() }
        mButton = view.findViewById(R.id.imageButton)
        mButton!!.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun startAnimation() {
        val sunYStart = mSunView!!.top.toFloat()
        val sunYEnd = mSkyView!!.height.toFloat()
        val starsYStart = mStarsView!!.top.toFloat()
        val starsYEnd = mTreeView!!.top.toFloat()
        val starYStart = mStarView!!.top.toFloat()
        val starYEnd = mStarView!!.bottom.toFloat()
        val marsYStart = mMarsView!!.top.toFloat()
        val marsYEnd = mStarsView!!.bottom.toFloat()
        val heightAnimator = ObjectAnimator
            .ofFloat(mSunView, "y", sunYStart, sunYEnd)
            .setDuration(400)
        heightAnimator.interpolator = AccelerateInterpolator(2F)
        val starsAnimator = ObjectAnimator
            .ofFloat(mStarsView, "y", starsYStart,starsYEnd)
            .setDuration(1000)
        starsAnimator.interpolator = AccelerateInterpolator(2F)
        val marsAnimatorY = ObjectAnimator
            .ofFloat(mMarsView, "y", marsYStart)
            .setDuration(1000)
        marsAnimatorY.interpolator = AccelerateInterpolator(2F)
        val marsAnimatorX = ObjectAnimator
            .ofFloat(mMarsView, "x", marsYEnd)
            .setDuration(1000)
        marsAnimatorX.interpolator = AccelerateInterpolator(2F)
        val starAnimator = ObjectAnimator
            .ofFloat(mStarView, "y", starYStart, starYEnd)
            .setDuration(300)
        starAnimator.interpolator = AccelerateInterpolator(2F)
        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor", mBlackSkyColor, mSunsetSkyColor)
            .setDuration(1500)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())
        val buttonAnimator = ObjectAnimator
            .ofInt(mButton, "backgroundColor",
                mBlackSkyColor, mSunsetSkyColor, mBlueSky,mSunsetSkyColor,mBlackSkyColor)
            .setDuration(4000)
        buttonAnimator.setEvaluator(ArgbEvaluator())
        val sunAnimator = ObjectAnimator
            .ofInt(mSunView, "backgroundColor", mBlackSkyColor, mWhiteColor)
            .setDuration(400)
        sunAnimator.setEvaluator(ArgbEvaluator())
        val trackAnimator = ObjectAnimator
            .ofInt(mTrackView, "backgroundColor",  mBlackSkyColor,
                mRedColor,mBlueSky,mSunsetSkyColor)
            .setDuration(500)
        trackAnimator.setEvaluator(ArgbEvaluator())
        val nightSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor",  mSunsetSkyColor, mBlueSky,mWhiteColor,mBlueSky,mSunsetSkyColor)
            .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())
        val animatorSet = AnimatorSet()
        animatorSet
            .play(starAnimator)
            .before(starsAnimator)
             .before(trackAnimator)
             .before(heightAnimator)
            .before(sunAnimator)
            .before(sunsetSkyAnimator)
            .before(nightSkyAnimator)
            .before(buttonAnimator)
        animatorSet.start()
        val animatorMars = AnimatorSet()
        animatorMars.playTogether(marsAnimatorX,marsAnimatorY)
        animatorMars.start()
    }

    override fun onResume() {
        super.onResume()
        onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(): StarFragment {
            return StarFragment()
        }
    }


}