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
    private var mMeteoriteViewZero: View? =null

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
        path.cubicTo(meteoriteXStart,meteoriteYStart, 800F,800F,900F, 600F)
        path.cubicTo(meteoriteXStart,meteoriteYStart, 670F,700F,600F, 1050F)
        val pathL = Path()
        pathL.cubicTo(moonXStart,moonYStart, 1000F,1000F,10F, 15F)
        pathL.cubicTo(moonXStart,moonYStart, 500F,1000F,520F, 2000F)
        pathL.cubicTo(moonXStart,moonYStart, 800F,700F,520F, 250F)
        val pathM = Path()
        pathM.cubicTo(620F,200F, 1F,1F,1F, 1F)
        pathM.cubicTo(1F,1F, 1F,150F,1F, 1F)

        val path1 = Path()
        path1.cubicTo(520F,250F, 500F,200F,520F, 250F)
        path1.cubicTo(35F,35F, 100F,1F,165F, 35F)
        path1.cubicTo(200F,100F, 165F,165F,100F, 200F)
        path1.cubicTo(35F,165F, 1F,100F,35F, 35F)
        val path2 = Path()
        path2.cubicTo(520F,250F, 500F,200F,520F, 250F)
        path2.cubicTo(100F,1F, 165F,35F,200F, 100F)
        path2.cubicTo(165F,165F, 100F,200F,35F, 165F)
        path2.cubicTo(1F,100F, 35F,35F,100F, 1F)
        val path3 = Path()
        path3.cubicTo(520F,250F, 500F,200F,520F, 250F)
        path3.cubicTo(165F,35F, 200F,100F,165F, 165F)
        path3.cubicTo(100F,200F, 35F,165F,1F, 100F)
        path3.cubicTo(35F,35F, 100F,1F,165F, 35F)
        val path4 = Path()
        path4.cubicTo(520F,250F, 500F,200F,520F, 250F)
        path4.cubicTo(200F,100F, 165F,165F,100F, 200F)
        path4.cubicTo(35F,165F, 1F,100F,35F, 35F)
        path4.cubicTo(100F,1F, 165F,35F,200F, 100F)
        val path5 = Path()
        path5.cubicTo(520F,250F, 500F,200F,520F, 250F)
        path5.cubicTo(165F,165F, 100F,200F,35F, 165F)
        path5.cubicTo(1F,100F, 35F,35F,100F, 1F)
        path5.cubicTo(165F,35F, 200F,100F,165F, 165F)
        val path6 = Path()
        path6.cubicTo(520F,250F, 500F,200F,520F, 250F)
        path6.cubicTo(100F,200F, 35F,165F,1F, 100F)
        path6.cubicTo(35F,35F, 100F,1F,165F, 35F)
        path6.cubicTo(200F,100F, 165F,165F,100F, 200F)
        val path7 = Path()
        path7.cubicTo(520F,250F, 500F,200F,520F, 250F)
        path7.cubicTo(35F, 165F,1F,100F,35F, 35F)
        path7.cubicTo(100F,1F, 165F,35F,200F, 100F)
        path7.cubicTo(165F,165F, 100F,200F,35F, 165F)
        val path8 = Path()
        path8.cubicTo(520F,250F, 500F,200F,520F, 250F)
        path8.cubicTo(1F,100F, 35F,35F,100F, 1F)
        path8.cubicTo(165F,35F, 200F,100F,165F, 165F)
        path8.cubicTo(100F,200F, 35F,165F,1F, 100F)

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
        val meteoriteAnimator = ObjectAnimator
            .ofFloat(mMeteoriteView, "x", "y",path)
            .setDuration(6000)
        meteoriteAnimator.interpolator = DecelerateInterpolator(2F)
        val meteoriteAnimator2 = ObjectAnimator
            .ofFloat(mMeteoriteViewZero, "x", "y",pathM)
            .setDuration(5500)
        meteoriteAnimator2.interpolator = DecelerateInterpolator(2F)
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
            .with(iconAnimator1)
            .with(iconAnimator2)
            .with(iconAnimator3)
            .with(iconAnimator4)
            .with(iconAnimator5)
            .with(iconAnimator6)
            .with(iconAnimator7)
            .with(iconAnimator8)
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