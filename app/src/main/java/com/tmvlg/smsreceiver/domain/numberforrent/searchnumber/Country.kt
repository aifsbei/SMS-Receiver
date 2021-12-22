package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

data class Country (
    var name: String = "",
    var code: Int = 0,
    var new: Boolean = false,
    var enabled: Boolean = true,
    var totalNumbers: Int = 0,
    var iconPath: String = "",
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}