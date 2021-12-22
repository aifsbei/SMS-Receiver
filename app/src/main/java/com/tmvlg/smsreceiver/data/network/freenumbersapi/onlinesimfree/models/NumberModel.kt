package com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models


import com.google.gson.annotations.SerializedName

data class NumberModel(
    val country: Int, // 7
    @SerializedName("country_text")
    val countryText: String, // Россия
    @SerializedName("data_humans")
    val dataHumans: String, // 3 дня назад
    @SerializedName("full_number")
    val fullNumber: String, // +79017320741
    val maxdate: String, // 2021-12-13 14:45:51
    val number: String, // 9017320741
    val status: String, // disabled
    @SerializedName("updated_at")
    val updatedAt: String // 2021-12-16 21:10:55
)