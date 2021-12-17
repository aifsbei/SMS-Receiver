package com.tmvlg.smsreceiver.domain.numberforrent

class GetNumberForRentUseCase(private val numberForRentRepository: NumberForRentRepository) {

    fun getNumberForRent(numberForRentId: Int): NumberForRent {
        return numberForRentRepository.getNumberForRent(numberForRentId)
    }
}