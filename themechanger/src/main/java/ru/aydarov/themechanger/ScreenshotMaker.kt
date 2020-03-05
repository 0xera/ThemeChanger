package ru.aydarov.themechanger

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

/**
 * @author Aydarov Askhar 2020
 */
object ScreenshotMaker {
    fun takeScreenshot(view: View): Bitmap = with(view) {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }
}
