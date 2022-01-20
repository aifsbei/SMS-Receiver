package com.tmvlg.smsreceiver.data.network.freenumbersapi.smska.unused

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


private const val BASE_URL = "https://smska.us"

var gson = GsonBuilder()
    .setLenient()
    .create()

private var retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

object SmskaApi {
    val retrofitService: SmskaApiService by lazy {
        retrofit.create(SmskaApiService::class.java)
    }
}