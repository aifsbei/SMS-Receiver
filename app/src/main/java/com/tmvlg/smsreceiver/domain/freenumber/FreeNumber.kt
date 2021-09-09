package com.tmvlg.smsreceiver.domain.freenumber

class FreeNumber (
    val call_number: String,
    val call_number_prefix: String,
    val counrty_name: String,
    val country_code: String,
    val origin: String,
    var icon_path: String,
    val type: Int = ITEM_TYPE,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
        const val HEADER_TYPE = 0
        const val ITEM_TYPE = 1
    }
}