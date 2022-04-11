package com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.models


import com.google.gson.annotations.SerializedName

data class ServiceModel(
    @SerializedName("code")
    val code: Int, // 1
    @SerializedName("count")
    val count: Int, // 2176
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("popular")
    val popular: Boolean, // false
    @SerializedName("price")
    val price: Float, // 50
    @SerializedName("service")
    val service: String, // Facebook
    @SerializedName("slug")
    val slug: String // 3223
)