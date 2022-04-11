package com.tmvlg.smsreceiver.util

import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Service

fun MutableSet<Service>.addUniqueOrUpdateCurrent(other: Service) {
    val current = this.find {
        it == other
    }
    if (current == null) {
        this.add(other)
    } else {
        current.numbersCount += other.numbersCount
        current.minPrice = minOf(current.minPrice, other.minPrice)
        current.popular = current.popular || other.popular
        this.add(current)
    }
}