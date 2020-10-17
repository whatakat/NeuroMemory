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
import android.view.animation.*
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
    private var mMeteoriteView2: View? =null
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
        mMeteoriteView2 = view.findViewById(R.id.meteorite2)
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
            mButton!!.isEnabled = false
            mButton!!.alpha = 0.10F
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ObjectAnimatorBinding")
    private fun startAnimation() {
        val sunYStart = mLightView!!.top.toFloat()
        val starsYStart = mStarsView!!.top.toFloat()
        val starsYEnd = mTreeView!!.top.toFloat()
        val moonYStart = mMoonView!!.top.toFloat()
        val moonXStart = mMoonView!!.left.toFloat()
        val meteoriteYStart = mMeteoriteView!!.top.toFloat()
        val meteoriteXStart = mMeteoriteView!!.left.toFloat()
        val buttonYStart = mButton!!.top.toFloat()
        val buttonXStart = mButton!!.bottom.toFloat()
        val path = Path()
         path.cubicTo(meteoriteXStart,meteoriteYStart, 90F,400F,1000F, 1000F)
        path.cubicTo(meteoriteXStart,meteoriteYStart, 700F,200F,10F, 60F)
        path.cubicTo(meteoriteXStart,meteoriteYStart, 900F,4000F,5F, 45F)
        val pathL = Path()
        pathL.cubicTo(moonXStart,moonYStart, 1000F,1000F,10F, 15F)
        pathL.cubicTo(moonXStart,moonYStart, 500F,1000F,520F, 2000F)
        pathL.cubicTo(moonXStart,moonYStart, 800F,700F,520F, 250F)
        val pathM = Path()
        pathM.cubicTo(meteoriteXStart,meteoriteYStart, 100F,100F,600F, 1800F)
        pathM.cubicTo(meteoriteXStart,meteoriteYStart, 1000F,1000F,110F, 45F)

        val lightAnimator = ObjectAnimator
            .ofFloat(mLightView, "y", sunYStart, buttonXStart)
            .setDuration(1500)
        lightAnimator.interpolator = AccelerateInterpolator(2F)
        val starsAnimator = ObjectAnimator
            .ofFloat(mStarsView, "y", starsYStart,starsYEnd)
            .setDuration(1000)
        starsAnimator.interpolator = DecelerateInterpolator(1F)
        val meteoriteAnimator = ObjectAnimator
            .ofFloat(mMeteoriteView, "x", "y",path)
            .setDuration(3000)
        meteoriteAnimator.interpolator = DecelerateInterpolator(2F)
        val meteoriteAnimator2 = ObjectAnimator
            .ofFloat(mMeteoriteView2, "x", "y",pathM)
            .setDuration(4500)
        meteoriteAnimator.interpolator = DecelerateInterpolator(2F)
        val moonAnimator = ObjectAnimator
            .ofFloat(mMoonView, "x", "y",pathL)
            .setDuration(2500)
        moonAnimator.interpolator = DecelerateInterpolator(2F)
        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor", mBlackSkyColor, mSunsetSkyColor)
            .setDuration(1800)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())
        val buttonAnimator = ObjectAnimator
            .ofFloat(mButton, "y", buttonXStart, buttonYStart)
            .setDuration(1000)
        buttonAnimator.interpolator = BounceInterpolator()
        val trackAnimator = ObjectAnimator
            .ofInt(mTrackView, "backgroundColor",  mBlackSkyColor,
                mRedColor,mBlueSky,mSunsetSkyColor,mWhiteColor)
            .setDuration(2000)
        trackAnimator.setEvaluator(ArgbEvaluator())
        val nightSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor",  mSunsetSkyColor, mBlueSky,mWhiteColor,mBlackSkyColor)
            .setDuration(1000)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())
        val rotateArrow = AnimationUtils.loadAnimation(this.context,R.anim.rotate_animation)
        val animatorSet = AnimatorSet()
        animatorSet
            .play(moonAnimator)
            .with(meteoriteAnimator)
            .with(meteoriteAnimator2)
            .before(lightAnimator)
            .with(starsAnimator)
             .with(trackAnimator)
            .before(nightSkyAnimator)
            .with(sunsetSkyAnimator)
            .before(buttonAnimator)
        animatorSet.start()
        mLightView!!.alpha = 0.70F
        mLightView!!.animation = rotateArrow
        mButton!!.alpha = 0.30F

    }

    override fun onResume() {
        super.onResume()
        mButton!!.isEnabled = true
        onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(): StarFragment {
            return StarFragment()
        }
    }


}