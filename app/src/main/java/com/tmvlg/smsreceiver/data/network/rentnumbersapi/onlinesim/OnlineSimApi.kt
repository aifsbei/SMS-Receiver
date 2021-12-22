package com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://onlinesim.ru/"

private var retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object OnlineSimApi {
    val retrofitService: OnlineSimApiService by lazy {
        retrofit.create(
            OnlineSimApiService::class.java)
    }
}