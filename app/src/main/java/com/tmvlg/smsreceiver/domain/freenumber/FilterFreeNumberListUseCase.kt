package com.tmvlg.smsreceiver.domain.freenumber

class FilterFreeNumberListUseCase(private val freeNumberRepository: FreeNumberRepository) {

    fun filterFreeNumberList(query: String) {
        freeNumberRepository.filterFreeNumberList(query)
    }
}