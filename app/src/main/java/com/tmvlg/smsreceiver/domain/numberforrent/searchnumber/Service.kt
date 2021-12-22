package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

data class Service(
    var numbersCount: Int = 0,
    var popular: Boolean = false,
    var code: Int = 0,
    var price: Int = 0,
    var id: Int = 0,
    var serviceName: String = "",
    var slug: String = ""
)