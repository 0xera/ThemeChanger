package ru.aydarov.themechanger

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.aydarov.themechanger.databinding.ActivityThemeChangeBinding
import kotlin.math.hypot


internal class ThemeChangerActivity : AppCompatActivity() {
    private lateinit var ivBackground: ImageView
    private var animRunnable = {
        ivBackground.setImageBitmap(ThemeChanger.lruCache.get(BITMAP_CACHE_KEY))
        revealEffectImage()
    }

    private val listenerAnimator: AnimatorListener by lazy {
        object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                LocalBroadcastManager.getInstance(this@ThemeChangerActivity)
                    .sendBroadcast(Intent(ACTION_THEME_CHANGE))
            }

            override fun onAnimationEnd(animation: Animator) {
                ivBackground.visibility = View.INVISIBLE
                this@ThemeChangerActivity.finish()
                overridePendingTransition(0, 0)
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ivBackground = ActivityThemeChangeBinding.inflate(layoutInflater).root
        setContentView(ivBackground)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        ivBackground.post(animRunnable)
        super.onCreate(savedInstanceState)
    }


    private fun revealEffectImage() {
        val coords = intent.getParcelableExtra(COORDINATES_EXTRA) ?: Coordinates(
            ivBackground.width / 2,
            ivBackground.height / 2
        )
        val animDuration = intent.getIntExtra(DURATION_ANIM_EXTRA, DURATION_ANIM)
        val startRadius = hypot(
            ivBackground.width.toFloat(),
            ivBackground.height.toFloat()
        )
        ViewAnimationUtils.createCircularReveal(
            ivBackground,
            coords.posX,
            coords.posY,
            startRadius,
            0f
        ).apply {
            duration = animDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(listenerAnimator)
            start()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ivBackground.removeCallbacks(animRunnable)
    }

    companion object {

        @JvmStatic
        internal fun createIntent(
            context: Context,
            coordinates: Coordinates,
            duration: Int,
        ) = Intent(context, ThemeChangerActivity::class.java)
            .putExtra(COORDINATES_EXTRA, coordinates)
            .putExtra(DURATION_ANIM_EXTRA, duration)
    }
}

