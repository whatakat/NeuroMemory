package com.bankmtk.neuromemory.ui.splash

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Path
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.Fragment
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.ui.main.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StarFragment: Fragment() {
    private var mButton: FloatingActionButton? =null
    private var mSceneView: View? = null
    private var mLightView: View? = null
    private var mSkyView: View? = null
    private var mTrackView: View? = null
    private var mStarsView: View? =null
    private var mTreeView: View? =null
    private var mMeteoriteView: View? =null
    private var mMeteoriteViewZero: View? =null

    private var mMeteoriteView0: View? =null
    private var mMeteoriteView1: View? =null
    private var mMeteoriteView2: View? =null
    private var mMeteoriteView3: View? =null
    private var mMeteoriteView4: View? =null
    private var mMeteoriteView5: View? =null
    private var mMeteoriteView6: View? =null
    private var mMeteoriteView7: View? =null
    private var mMeteoriteView8: View? =null


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

        mMeteoriteView0 = view.findViewById(R.id.moon0)
        mMeteoriteView1 = view.findViewById(R.id.moon1)
        mMeteoriteView2 = view.findViewById(R.id.moon2)
        mMeteoriteView3 = view.findViewById(R.id.moon3)
        mMeteoriteView4 = view.findViewById(R.id.moon4)
        mMeteoriteView5 = view.findViewById(R.id.moon5)
        mMeteoriteView6 = view.findViewById(R.id.moon6)
        mMeteoriteView7 = view.findViewById(R.id.moon7)
        mMeteoriteView8 = view.findViewById(R.id.moon8)

        mMeteoriteViewZero = view.findViewById(R.id.meteorite2)
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
        val drawablePointB: Drawable =  mButton!!.drawable
        if (drawablePointB is Animatable){
            drawablePointB.start()
        }
        return view

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ObjectAnimatorBinding")
    private fun startAnimation() {
        val sunYStart = mLightView!!.top.toFloat()
        val starsYStart = mStarsView!!.top.toFloat()
        val starsYEnd = mTreeView!!.top.toFloat()
        val buttonYStart = mButton!!.top.toFloat()
        val buttonXStart = mButton!!.bottom.toFloat()
        val path = Path()
        path.cubicTo(520F,1750F, 385F,1885F,345F, 1925F)
        path.cubicTo(385F,2060F, 529F,2100F,560F, 2060F)
        path.cubicTo(695F,1925F, 560F,1885F,450F, 350F)
        val path0 = Path()
        path0.cubicTo(520F,1750F, 385F,1885F,345F, 1925F)
        path0.cubicTo(385F,2060F, 529F,2100F,560F, 2060F)
        path0.cubicTo(695F,1925F, 560F,1885F,670F, 520F)
        val pathL = Path()
        pathL.cubicTo(520F,1750F, 385F,1885F,345F, 1925F)
        pathL.cubicTo(385F,2060F, 529F,2100F,560F, 2060F)
        pathL.cubicTo(695F,1925F, 560F,1885F,780F, 560F)
        val pathM = Path()
        pathM.cubicTo(520F,1930F, 500F,200F,520F, 1930F)
        pathM.cubicTo(10F,50F, 150F,50F,10F, 50F)
        pathM.cubicTo(200F,50F, 1F,50F,10F, 50F)

        val path1 = Path()
        path1.cubicTo(520F,1900F, 500F,200F,350F, 1900F)
        path1.cubicTo(35F,35F, 100F,1F,165F, 35F)
        path1.cubicTo(200F,100F, 165F,165F,100F, 200F)
        path1.cubicTo(35F,165F, 1F,100F,350F, 350F)
        val path2 = Path()
        path2.cubicTo(520F,1900F, 500F,200F,350F, 1900F)
        path2.cubicTo(100F,1F, 165F,35F,200F, 100F)
        path2.cubicTo(165F,165F, 100F,200F,35F, 165F)
        path2.cubicTo(1F,100F, 35F,35F,1000F, 10F)
        val path3 = Path()
        path3.cubicTo(520F,1900F, 500F,200F,350F, 1900F)
        path3.cubicTo(165F,35F, 200F,100F,165F, 165F)
        path3.cubicTo(100F,200F, 35F,165F,1F, 100F)
        path3.cubicTo(35F,35F, 100F,1F,1650F, 350F)
        val path4 = Path()
        path4.cubicTo(520F,1900F, 500F,200F,350F, 1900F)
        path4.cubicTo(200F,100F, 165F,165F,100F, 200F)
        path4.cubicTo(35F,165F, 1F,100F,35F, 35F)
        path4.cubicTo(100F,1F, 165F,35F,2000F, 1000F)
        val path5 = Path()
        path5.cubicTo(520F,1900F, 500F,200F,350F, 1900F)
        path5.cubicTo(165F,165F, 100F,200F,35F, 165F)
        path5.cubicTo(1F,100F, 35F,35F,100F, 1F)
        path5.cubicTo(165F,35F, 200F,100F,1650F, 1650F)
        val path6 = Path()
        path6.cubicTo(520F,1900F, 500F,200F,350F, 1900F)
        path6.cubicTo(100F,200F, 35F,165F,1F, 100F)
        path6.cubicTo(35F,35F, 100F,1F,165F, 35F)
        path6.cubicTo(200F,100F, 165F,165F,1000F, 2000F)
        val path7 = Path()
        path7.cubicTo(520F,1900F, 500F,200F,350F, 1900F)
        path7.cubicTo(35F, 165F,1F,100F,35F, 35F)
        path7.cubicTo(100F,1F, 165F,35F,200F, 100F)
        path7.cubicTo(165F,165F, 100F,200F,350F, 1650F)
        val path8 = Path()
        path8.cubicTo(520F,1900F, 500F,200F,350F, 1900F)
        path8.cubicTo(1F,100F, 35F,35F,100F, 1F)
        path8.cubicTo(165F,35F, 200F,100F,165F, 165F)
        path8.cubicTo(100F,200F, 35F,165F,10F, 1000F)

        val iconAnimator1 = ObjectAnimator
            .ofFloat(mMeteoriteView1, "x", "y",path1)
            .setDuration(6000)
        iconAnimator1.interpolator = DecelerateInterpolator(2F)
        val iconAnimator2 = ObjectAnimator
            .ofFloat(mMeteoriteView2, "x", "y",path2)
            .setDuration(6000)
        iconAnimator2.interpolator = DecelerateInterpolator(2F)
        val iconAnimator3 = ObjectAnimator
            .ofFloat(mMeteoriteView3, "x", "y",path3)
            .setDuration(6000)
        iconAnimator3.interpolator = DecelerateInterpolator(2F)
        val iconAnimator4 = ObjectAnimator
            .ofFloat(mMeteoriteView4, "x", "y",path4)
            .setDuration(6000)
        iconAnimator4.interpolator = DecelerateInterpolator(2F)
        val iconAnimator5 = ObjectAnimator
            .ofFloat(mMeteoriteView5, "x", "y",path5)
            .setDuration(6000)
        iconAnimator5.interpolator = DecelerateInterpolator(2F)
        val iconAnimator6 = ObjectAnimator
            .ofFloat(mMeteoriteView6, "x", "y",path6)
            .setDuration(6000)
        iconAnimator6.interpolator = DecelerateInterpolator(2F)
        val iconAnimator7 = ObjectAnimator
            .ofFloat(mMeteoriteView7, "x", "y",path7)
            .setDuration(6000)
        iconAnimator7.interpolator = DecelerateInterpolator(2F)
        val iconAnimator8 = ObjectAnimator
            .ofFloat(mMeteoriteView8, "x", "y",path8)
            .setDuration(6000)
        iconAnimator8.interpolator = DecelerateInterpolator(2F)

        val lightAnimator = ObjectAnimator
            .ofFloat(mLightView, "y", sunYStart, buttonXStart)
            .setDuration(1500)
        lightAnimator.interpolator = AccelerateInterpolator(2F)
        val starsAnimator = ObjectAnimator
            .ofFloat(mStarsView, "y", starsYStart,starsYEnd)
            .setDuration(1000)
        starsAnimator.interpolator = DecelerateInterpolator(1F)
        val meteoriteAnimator0 = ObjectAnimator
            .ofFloat(mMeteoriteView0, "x", "y",path0)
            .setDuration(6000)
        meteoriteAnimator0.interpolator = DecelerateInterpolator(2F)
        val meteoriteAnimator = ObjectAnimator
            .ofFloat(mMeteoriteView, "x", "y",path)
            .setDuration(6000)
        meteoriteAnimator.interpolator = DecelerateInterpolator(2F)
        val meteoriteAnimator2 = ObjectAnimator
            .ofFloat(mMeteoriteViewZero, "x", "y",pathM)
            .setDuration(5000)
        meteoriteAnimator2.interpolator = DecelerateInterpolator(2F)
        val moonAnimator = ObjectAnimator
            .ofFloat(mMoonView, "x", "y",pathL)
            .setDuration(5500)
        moonAnimator.interpolator = DecelerateInterpolator(2F)
        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor", mBlackSkyColor, mSunsetSkyColor)
            .setDuration(1800)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())
        val buttonAnimator = ObjectAnimator
            .ofFloat(mButton, "y", buttonXStart, buttonYStart)
            .setDuration(3000)
        buttonAnimator.interpolator = DecelerateInterpolator()
        val trackAnimator = ObjectAnimator
            .ofInt(mTrackView, "backgroundColor",  mBlackSkyColor,
                mRedColor,mBlueSky,mSunsetSkyColor,mBlackSkyColor)
            .setDuration(2000)
        trackAnimator.setEvaluator(ArgbEvaluator())
        val nightSkyAnimator = ObjectAnimator
            .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor,mBlackSkyColor)
            .setDuration(6000)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())
        val rotateArrow = AnimationUtils.loadAnimation(this.context,R.anim.rotate_animation)
        val animatorSet = AnimatorSet()
        animatorSet
            .play(moonAnimator)
            .with(meteoriteAnimator0)
            .with(meteoriteAnimator)
            .with(meteoriteAnimator2)
            .with(iconAnimator1)
            .with(iconAnimator2)
            .with(iconAnimator3)
            .with(iconAnimator4)
            .with(iconAnimator5)
            .with(iconAnimator6)
            .with(iconAnimator7)
            .with(iconAnimator8)
            .with(lightAnimator)
            .with(starsAnimator)
             .with(trackAnimator)
            .with(nightSkyAnimator)
            .with(sunsetSkyAnimator)
            .with(buttonAnimator)
        animatorSet.start()
        mLightView!!.alpha = 0.70F
        mLightView!!.animation = rotateArrow
        mButton!!.alpha = 0.20F

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