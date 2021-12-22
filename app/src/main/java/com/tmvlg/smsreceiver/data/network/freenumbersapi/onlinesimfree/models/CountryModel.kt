package com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models


import com.google.gson.annotations.SerializedName

data class CountryModel(
    val country: Int, // 7
    @SerializedName("country_text")
    val countryText: String // Россия
)