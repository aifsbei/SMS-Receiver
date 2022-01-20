package com.tmvlg.smsreceiver.data.network.freenumbersapi.smska.unused.models

import com.google.gson.annotations.SerializedName

data class PayloadModel(
    @SerializedName("n")
    val callNumber: String
)