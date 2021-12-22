package com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree

import com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models.FreeCountriesResponse
import com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models.MessagesResponse
import com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models.NumbersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OnlineSimFreeApiService {

    @GET("api/getFreeCountryList")
    fun getCountryNames(): Call<FreeCountriesResponse>

    @GET("api/getFreePhoneList")
    fun getFreeNumbers(@Query("country") countryCode: Int): Call<NumbersResponse>

    @GET("api/getFreeMessageList")
    fun getFreeMessages(
        @Query("phone") phoneNumber: String,
        @Query("country") countryName: String,
        @Query("lang") language: String = "en"
    ): Call<MessagesResponse>

}