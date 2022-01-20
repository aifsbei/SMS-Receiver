package com.tmvlg.smsreceiver.data.network.freenumbersapi.smska.unused

import com.tmvlg.smsreceiver.data.network.freenumbersapi.smska.unused.models.PayloadModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SmskaApiService {
    @POST("/AjaxPublic/sms/")
    fun getMessages(@Body payload: PayloadModel): Call<String>
}