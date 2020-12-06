package ru.aydarov.themechanger

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

internal fun View.takeScreenshot(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}

internal fun View.findLocationOfCenterOnTheScreen(): Coordinates {
    val positions = intArrayOf(0, 0)
    getLocationInWindow(positions)
    positions[0] = positions[0] + width / 2
    positions[1] = positions[1] + height / 2
    return Coordinates(positions[0], positions[1])
}

