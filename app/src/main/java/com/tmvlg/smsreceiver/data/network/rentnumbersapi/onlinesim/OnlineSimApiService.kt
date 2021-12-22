package com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim

import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.models.CountryModel
import com.tmvlg.smsreceiver.internal.constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OnlineSimApiService {

    @GET("api/getNumbersStats.php")
    fun getCountries(
        @Query("apikey") apiKey: String = constants.ONLINESIM_API_KEY,
        @Query("country") country: String = "all"
        ): Call<Map<String, CountryModel>>

}