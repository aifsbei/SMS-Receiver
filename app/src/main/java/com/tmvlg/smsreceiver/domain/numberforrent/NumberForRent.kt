package com.tmvlg.smsreceiver.domain.numberforrent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "number_for_rent")
data class NumberForRent (
    val call_number: String,
    val call_number_prefix: String,
    val counrty_name: String,
    val country_code: String,
    var country_icon: String,
    val service_icon: Int,
    val service_name: String,
    var time_left: Int,
    @PrimaryKey(autoGenerate = false)
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
        const val HEADER_TYPE = 0
        const val ITEM_TYPE = 1
    }
}