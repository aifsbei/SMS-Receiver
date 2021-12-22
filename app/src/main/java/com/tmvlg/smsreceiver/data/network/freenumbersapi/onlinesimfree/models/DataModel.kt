package com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models


import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("created_at")
    val createdAt: String, // 2021-12-17 01:12:07
    @SerializedName("data_humans")
    val dataHumans: String, // 2 минуты назад
    @SerializedName("in_number")
    val inNumber: String, // MAGNIT
    @SerializedName("my_number")
    val myNumber: Long, // 9017320741
    val text: String // MAGNIT: 00604 - ваш код подтверждения
)