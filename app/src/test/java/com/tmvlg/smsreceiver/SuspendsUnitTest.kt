package com.tmvlg.smsreceiver

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test

public class SuspendsUnitTest {

    @Test
    fun call() {
        Log.d("1", "Started!111!")
        GlobalScope.launch {
            val firstResult = first_delay()
            val secondResult = second_delay()
            Log.d("1", "call: $firstResult)")
            Log.d("1", "call: $secondResult)")
        }
    }

    suspend fun first_delay(): String {
        delay(5000L)
        Log.d("1", "call: First finished)")
        return "first returned"
    }

    suspend fun second_delay(): String {
        delay(5000L)
        Log.d("1", "call: Second finished)")
        return "second returned"
    }

}