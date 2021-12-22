package com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models


data class FreeCountriesResponse(
    val countries: List<CountryModel>,
    val response: Int // 1
)