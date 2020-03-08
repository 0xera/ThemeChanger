package ru.aydarov.themechanger

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_theme_change.*
import java.io.ByteArrayOutputStream
import java.lang.ref.WeakReference
import kotlin.math.hypot

/**
 * @author Aydarov Askhar 2020
 */
class ThemeChanger : AppCompatActivity() {
    private var mPositionX: Int = 0
    private var mPositionY: Int = 0

    private val listenerAnimator: AnimatorListener by lazy {
        object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                LocalBroadcastManager.getInstance(this@ThemeChanger)
                    .sendBroadcast(Intent(ACTION_THEME_CHANGE))
            }

            override fun onAnimationEnd(animation: Animator) {
                receiver?.clean()
                receiver = null
                ivScreenshot.visibility = View.INVISIBLE
                this@ThemeChanger.finish()
                overridePendingTransition(0, 0)
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_theme_change)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        intentData()
        ivScreenshot.post { this.revealEffectImage() }
        super.onCreate(savedInstanceState)
    }

    private fun intentData() = with(intent) {
        val bitmapData = getByteArrayExtra(IMG_EXTRA)
        bitmapData?.let {
            ivScreenshot.setImageBitmap(BitmapFactory.decodeByteArray(it, 0, it.size))
            mPositionX = getIntExtra(X_POS, -1)
            mPositionY = getIntExtra(Y_POS, -1)
        }


    }


    private fun revealEffectImage() {
        calculateValues()
        startAnimation()
    }

    private fun startAnimation() {
        ViewAnimationUtils.createCircularReveal(
                ivScreenshot,
                mPositionX,
                mPositionY,
                calculateValues(),
                0f
            )
            .apply {
                duration = DURATION_ANIM.toLong()
                interpolator = DecelerateInterpolator()
                addListener(listenerAnimator)
                start()
            }
    }

    private fun calculateValues(): Float {
        return hypot(ivScreenshot.width.toFloat(), ivScreenshot.height.toFloat())
    }

    override fun onDestroy() {
        receiver?.let {
            it.clean()
            null
        }

        super.onDestroy()
    }

    companion object {

        private var mActionDown: Boolean = false
        private const val IMG_EXTRA = "img"
        private const val X_POS = "X_POS"
        private const val Y_POS = "Y_POS"
        private var DURATION_ANIM = 400
        private const val ACTION_THEME_CHANGE = "ru.magic.+theme-change"
        private var receiver: ThemeChangerReceiver? = null

        private class ThemeChangerReceiver(private var referenceActivity: WeakReference<AppCompatActivity>?) :
            BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                referenceActivity?.get()?.recreate()
            }

            fun clean() {
                referenceActivity?.get()
                    ?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(this) }
                referenceActivity = null
            }
        }

        private fun changeTheme(context: Context, xButton: Int, yButton: Int) = with(context) {
            createReceiver(context)
            val bs = createScreenshot()
            val intent = createIntent(bs, xButton, yButton)
            startActivity(intent)
        }

        private fun Context.createIntent(
            bs: ByteArrayOutputStream,
            xButton: Int,
            yButton: Int
        ): Intent {
            return Intent(this, ThemeChanger::class.java).apply {
                putExtra(IMG_EXTRA, bs.toByteArray())
                putExtra(X_POS, xButton)
                putExtra(Y_POS, yButton)
            }
        }

        private fun Context.createScreenshot(): ByteArrayOutputStream {
            val bs = ByteArrayOutputStream()
            ScreenshotMaker.takeScreenshot((this as AppCompatActivity).window.decorView)
                .compress(Bitmap.CompressFormat.JPEG, 100, bs)
            return bs
        }

        private fun createReceiver(context: Context) {
            receiver = ThemeChangerReceiver(WeakReference(context as AppCompatActivity))
            context.let {
                LocalBroadcastManager.getInstance(it)
                    .registerReceiver(receiver!!, IntentFilter(ACTION_THEME_CHANGE))
            }
        }

        @JvmStatic
        fun prepareToChange(context: Context, event: MotionEvent, duration: Int = DURATION_ANIM): Boolean {
            DURATION_ANIM = duration
            mActionDown =
                if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE)
                    true
                else if (event.action == MotionEvent.ACTION_UP && mActionDown) {
                    changeTheme(context, event.rawX.toInt(), event.rawY.toInt())
                    false
                } else false
            return !mActionDown

        }
    }

}
