package com.tmvlg.smsreceiver.domain.freenumber

class FreeNumber (
    val call_number: String,
    val call_number_prefix: String,
    val counrty_name: String,
    val country_code: String,
    val origin: String,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}