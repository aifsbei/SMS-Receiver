package com.tmvlg.smsreceiver.domain.numberforrent.searchnumber

import androidx.lifecycle.LiveData
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository

class GetCountryListUseCase(private var searchCountryRepository: SearchCountryRepository) {

    fun getCountryList(): LiveData<List<Country>> {
        return searchCountryRepository.getCountryList()
    }
}