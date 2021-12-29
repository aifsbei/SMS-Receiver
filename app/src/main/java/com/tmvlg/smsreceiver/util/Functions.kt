package com.tmvlg.smsreceiver.util

import android.R.attr
import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import com.tmvlg.smsreceiver.R
import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log


fun Context.isDarkThemeOn(): Boolean {
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
}

//fun calculateAverageColor(bitmap: Bitmap): Int {
//    var redBucket: Long = 0
//    var greenBucket: Long = 0
//    var blueBucket: Long = 0
//    var pixelCount: Long = 0
//
//    for (y in 0 until bitmap.getHeight()) {
//        for (x in 0 until bitmap.getWidth()) {
//            val c: Int = bitmap.getPixel(x, y)
//            pixelCount++
//            redBucket += Color.red(c)
//            greenBucket += Color.green(c)
//            blueBucket += Color.blue(c)
//        }
//    }
//
//    return Color.rgb(
//        (redBucket / pixelCount).toInt(), (
//                greenBucket / pixelCount).toInt(), (
//                blueBucket / pixelCount).toInt()
//    )
//}

fun Bitmap.calculateAverageColor(): Int {
    val start = System.currentTimeMillis()
    var redBucket: Long = 0
    var greenBucket: Long = 0
    var blueBucket: Long = 0
    var pixelCount: Long = 0

    for (y in 0 until this.getHeight()) {
        for (x in 0 until this.getWidth()) {
            val c: Int = this.getPixel(x, y)
            pixelCount++
            redBucket += Color.red(c)
            greenBucket += Color.green(c)
            blueBucket += Color.blue(c)
        }
    }
    val end = System.currentTimeMillis() - start
    Log.d("1", "init_data: in $end millis") //too heavy function

    return Color.rgb(
        (redBucket / pixelCount).toInt(), (
                greenBucket / pixelCount).toInt(), (
                blueBucket / pixelCount).toInt()
    )
}

fun Context.getBitmap(drawableRes: Int): Bitmap {
    val drawable = resources.getDrawable(drawableRes)
    val canvas = Canvas()
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    canvas.setBitmap(bitmap)
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    drawable.draw(canvas)

    return bitmap
}