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
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import java.util.*


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

fun List<FreeNumber>.containsCountryName(countryName: String): Boolean {
    this.forEach {
        if (it.counrty_name == countryName)
            return true
    }
    return false
}

fun Date.getTimeAgo(timeZone: TimeZone? = null): String {
    val calendar = Calendar.getInstance()
    calendar.time = this

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val second = calendar.get(Calendar.SECOND)

    val currentCalendar = if (timeZone == null)
        Calendar.getInstance()
    else
        Calendar.getInstance(timeZone)

    val currentYear = currentCalendar.get(Calendar.YEAR)
    val currentMonth = currentCalendar.get(Calendar.MONTH)
    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
    val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = currentCalendar.get(Calendar.MINUTE)
    val currentSecond = currentCalendar.get(Calendar.SECOND)

    return if (year < currentYear) {
        val interval = currentYear - year
        if (interval == 1) "$interval year ago" else "$interval years ago"
    } else if (month < currentMonth) {
        val interval = currentMonth - month
        if (interval == 1) "$interval month ago" else "$interval months ago"
    } else if (day < currentDay) {
        val interval = currentDay - day
        if (interval == 1) "$interval day ago" else "$interval days ago"
    } else if (hour < currentHour) {
        val interval = currentHour - hour
        if (interval == 1) "$interval hour ago" else "$interval hours ago"
    } else if (minute < currentMinute) {
        val interval = currentMinute - minute
        if (interval == 1) "$interval minute ago" else "$interval minutes ago"
    } else if (second < currentSecond) {
        val interval = currentSecond - second
        if (interval == 1) "$interval second ago" else "$interval seconds ago"
    } else {
        "a moment ago"
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun checkConnection(connectivityManager: ConnectivityManager): Boolean {
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}

fun checkConnectionOld(connectivityManager: ConnectivityManager): Boolean {
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}

fun Context.isOnline(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        checkConnection(connectivityManager)
    } else {
        checkConnectionOld(connectivityManager)
    }
}