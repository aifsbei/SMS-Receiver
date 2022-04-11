package com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim

import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.models.CountryModel
import com.tmvlg.smsreceiver.internal.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface OnlineSimApiService {

    @GET("api/getNumbersStats.php")
    suspend fun getNumberStats(
        @Query("apikey") apiKey: String = Constants.ONLINESIM_API_KEY,
        @Query("country") country: String = "all"
    ): Map<String, CountryModel>

}