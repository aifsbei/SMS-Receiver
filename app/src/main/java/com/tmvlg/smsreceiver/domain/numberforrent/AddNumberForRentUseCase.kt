package com.tmvlg.smsreceiver.domain.numberforrent

class AddNumberForRentUseCase (private val numberForRentRepository: NumberForRentRepository) {

    fun addNumberForRent(numberForRent: NumberForRent) {
        numberForRentRepository.addNumberForRent(numberForRent)
    }
}