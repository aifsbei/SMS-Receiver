package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

data class Service(
    var numbersCount: Int = 0,
    var popular: Boolean = false,
    var code: Int = 0,
    var minPrice: Float = 0f,
    var id: Int = 0,
    var serviceName: String = "",
    var slug: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Service) {
            return false
        }
        return serviceName == other.serviceName
    }

    override fun hashCode(): Int {
        return serviceName.hashCode()
    }
}