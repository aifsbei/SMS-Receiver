package com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://onlinesim.ru/"

private var retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object OnlineSimFreeApi {
    val retrofitService: OnlineSimFreeApiService by lazy {
        retrofit.create(OnlineSimFreeApiService::class.java)
    }
}