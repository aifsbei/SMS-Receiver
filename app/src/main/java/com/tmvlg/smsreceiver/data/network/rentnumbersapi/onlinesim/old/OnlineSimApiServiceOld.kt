package com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.old

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tmvlg.smsreceiver.data.network.ConnectivityInterceptor
import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.old.pojo.GetNumResponseOld
import com.tmvlg.smsreceiver.internal.constants
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = constants.ONLINESIM_API_KEY

interface OnlineSimApiServiceOld {

    // https://onlinesim.ru/demo/api/getNum.php?apikey=59bfd866b2abc68711303e0d04d613fb&service=Gett

    @GET("getNum.php")
    fun getNum (
        @Query("service") serviceName: String
    ): Deferred<GetNumResponseOld>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): OnlineSimApiServiceOld {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apikey", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://onlinesim.ru/demo/api/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OnlineSimApiServiceOld::class.java)
        }
    }

}