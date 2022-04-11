package com.tmvlg.smsreceiver

import com.tmvlg.smsreceiver.data.NumberDataSource
import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.OnlineSimApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

public class OnlineSimUnitTest {
    @Test
    fun parseCountries() {
        runBlocking {
            val api = OnlineSimApi
            val ds = NumberDataSource(api)
            ds.fetchAvailableCountries(1)
                .catch {
                    println(it)
                }
                .collect {
                    it.forEach {
                        println(it)
                    }
                }
        }
    }

    @Test
    fun parseServices() {
        runBlocking {
            val api = OnlineSimApi
            val ds = NumberDataSource(api)
            ds.fetchServiceList()
                .catch {
                    println(it)
                }
                .collect {
                    it.forEach {
                        println(it)
                    }
                }
        }
    }
}