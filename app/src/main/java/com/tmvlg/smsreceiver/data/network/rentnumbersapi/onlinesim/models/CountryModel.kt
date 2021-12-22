package com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.models


import com.google.gson.annotations.SerializedName

data class CountryModel(
    @SerializedName("code")
    val code: Int, // 1
    @SerializedName("enabled")
    val enabled: Boolean, // true
    @SerializedName("name")
    val name: String, // usa
    @SerializedName("new")
    val new: Boolean, // false
    @SerializedName("position")
    val position: Int, // 22
    @SerializedName("services")
    val services: Map<String, ServiceModel>
)