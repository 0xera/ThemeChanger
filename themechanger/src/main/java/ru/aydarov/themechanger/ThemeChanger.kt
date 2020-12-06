package ru.aydarov.themechanger

import android.content.IntentFilter
import android.graphics.Bitmap
import android.util.LruCache
import android.view.View
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.lang.ref.WeakReference

object ThemeChanger {

    internal val lruCache = LruCache<String, Bitmap>(1)

    fun AppCompatActivity.changeTheme(
        view: View,
        @IntRange(from = 400, to = 1000) duration: Int = DURATION_ANIM,
        block: (() -> Unit)? = null
    ) {
        createReceiver()
        lruCache.put(BITMAP_CACHE_KEY, window.decorView.takeScreenshot())
        startActivity(
            ThemeChangerActivity.createIntent(
                this,
                view.findLocationOfCenterOnTheScreen(),
                duration
            )
        )
        block?.invoke()
    }

    private fun AppCompatActivity.createReceiver() {
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(
                ThemeChangerReceiver(WeakReference(this)),
                IntentFilter(ACTION_THEME_CHANGE)
            )
    }

}