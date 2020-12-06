package ru.aydarov.themechanger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.lang.ref.WeakReference

internal class ThemeChangerReceiver(
    private var referenceActivity: WeakReference<AppCompatActivity>?
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        referenceActivity?.get()?.recreate()
        referenceActivity?.get()
            ?.let {
                LocalBroadcastManager.getInstance(it).unregisterReceiver(this)
            }
        referenceActivity = null
    }
}