package com.bankmtk.neuromemory.ui.splash

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.Fragment
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.ui.main.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_splash.*

class StarFragment: Fragment() {
    private var mButton: FloatingActionButton? =null
    private var mSceneView: View? = null
    private var mLightView: View? = null
    private var mSkyView: View? = null
    private var mTrackView: View? = null
    private var mStarsView: View? =null
    private var mTreeView: View? =null
    private var mMeteoriteView: View? =null
    private var mMoonView: View? = null
    private var mBlackSkyColor = 0
    private var mSunsetSkyColor = 0
    private var mStarColor = 0
    private var mBlueSky = 0
    private var mWhiteColor = 0
    private var mRedColor = 0
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_splash, container, false)
        mSceneView = view
        mLightView = view.findViewById(R.id.starCenter)
        mSkyView = view.findViewById(R.id.sky)
        mTrackView = view.findViewById(R.id.trackCenter)
        mStarsView = view.findViewById(R.id.stars)
        mTreeView = view.findViewById(R.id.tree)
        mMeteoriteView = view.findViewById(R.id.meteorite)
        mMoonView = view.findViewById(R.id.moon)
        mBlackSkyColor = getColor(resources,R.color.black,null)
        mSunsetSkyColor = getColor(resources,R.color.night_sky, null)
        mStarColor = getColor(resources,R.color.yellow,null)
        mBlueSky = getColor(resources,R.color.blue_sky,null)
        mWhiteColor = getColor(resources,R.color.white,null)
        mRedColor = getColor(resources,R.color.red, null)
        mSceneView!!.setOnLongClickListener { startAnimation(); true }
        mButton = view.findViewById(R.id.imageButton)
        mButton!!.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ObjectAnimatorBinding")
    private fun startAnimation() {
        val sunYStart = mLightView!!.top.toFloat()
        val sunYEnd = mSkyView!!.height.toFloat()
        val starsYStart = mStarsView!!.top.toFloat()
        val starsYEnd = mTreeView!!.top.toFloat()
        val moonYStart = mMoonView!!.top.toFloat()
        val moonYEnd = mTreeView!!.bottom.toFloat()
        val meteoriteYStart = mMeteoriteView!!.top.toFloat()
        val meteoriteXStart = mMeteoriteView!!.left.toFloat()
        val buttonYStart = mButton!!.top.toFloat()
        val buttonXStart = mButton!!.bottom.toFloat()
        val path = Path()
         path.cubicTo(meteoriteXStart,meteoriteYStart, 800F,1800F,900F, 875F)

        val lightAnimator = ObjectAnimator
            .ofFloat(mLightView, "y", sunYStart, buttonYStart-81F)
            .setDuration(1500)
        lightAnimator.interpolator = AccelerateInterpolator(2F)
        val starsAnimator = ObjectAnimator
            .ofFloat(mStarsView, "y", starsYStart,starsYEnd)
            .setDuration(1000)
        starsAnimator.interpolator = DecelerateInterpolator(1F)
        val meteoriteAnimator = ObjectAnimator
            .ofFloat(mMeteoriteView, "x", "y",path)
            .setDuration(2000)
        meteoriteAnimator.interpolator = DecelerateInterpolator(2F)
        val moonAnimator = ObjectAnimator
            .ofFloat(mMoonView, "y", moonYStart,moonYEnd)
            .setDuration(1000)
        moonAnimator.interpolator = AnticipateInterpolator(2F)
        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor", mBlackSkyColor, mSunsetSkyColor)
            .setDuration(1800)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())
        val buttonAnimator = ObjectAnimator
            .ofFloat(mButton, "y", buttonXStart, buttonYStart)
            .setDuration(1600)
        buttonAnimator.interpolator = BounceInterpolator()
//        val sunAnimator = ObjectAnimator
//            .ofInt(mLightView, "backgroundColor", mBlackSkyColor)
//            .setDuration(2300)
//        sunAnimator.setEvaluator(ArgbEvaluator())
        val trackAnimator = ObjectAnimator
            .ofInt(mTrackView, "backgroundColor",  mBlackSkyColor,
                mRedColor,mBlueSky,mSunsetSkyColor)
            .setDuration(900)
        trackAnimator.setEvaluator(ArgbEvaluator())
        val nightSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor",  mSunsetSkyColor, mBlueSky,mWhiteColor)
            .setDuration(1800)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())
        val animatorSet = AnimatorSet()
        animatorSet
            .play(moonAnimator)
            .with(meteoriteAnimator)
            .before(lightAnimator)
            //.with(sunAnimator)
            .with(starsAnimator)
             .with(trackAnimator)
            .before(nightSkyAnimator)
            .with(sunsetSkyAnimator)
            .before(buttonAnimator)

        animatorSet.start()
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