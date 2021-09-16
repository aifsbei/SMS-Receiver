package com.tmvlg.smsreceiver.domain.freemessage

data class FreeMessage(
        val message_title: String,
        val message_text: String,
        val time_remained: String,
        var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}