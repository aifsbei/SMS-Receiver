package com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models


import com.google.gson.annotations.SerializedName

data class Messages(
    @SerializedName("current_page")
    val currentPage: Int, // 1
    val `data`: List<Data>,
    @SerializedName("first_page_url")
    val firstPageUrl: String, // https://onlinesim.ru/api/getFreeMessageList?page=1
    val from: Int, // 1
    @SerializedName("last_page")
    val lastPage: Int, // 565
    @SerializedName("last_page_url")
    val lastPageUrl: String, // https://onlinesim.ru/api/getFreeMessageList?page=565
    val links: List<Link>,
    @SerializedName("next_page_url")
    val nextPageUrl: String, // https://onlinesim.ru/api/getFreeMessageList?page=2
    val path: String, // https://onlinesim.ru/api/getFreeMessageList
    @SerializedName("per_page")
    val perPage: Int, // 10
    @SerializedName("prev_page_url")
    val prevPageUrl: Any, // null
    val to: Int, // 10
    val total: Int // 5645
)