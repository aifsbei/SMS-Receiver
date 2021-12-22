package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.LoadCountryListUseCase
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.DeleteCountryListUseCase
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.GetCountryListUseCase
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.SearchCountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchCountryViewModel(
    private val countryRepository: SearchCountryRepository
) : ViewModel() {

    private val loadCountryListUseCase = LoadCountryListUseCase(countryRepository)
    private val getCountryListUseCase = GetCountryListUseCase(countryRepository)
    private val deleteCountryListUseCase = DeleteCountryListUseCase(countryRepository)

    var countryList = getCountryListUseCase.getCountryList()

    fun loadCountries() = viewModelScope.launch(Dispatchers.IO) {
        loadCountryListUseCase.loadCountryList()
    }

    fun clearCountryList() {
        deleteCountryListUseCase.deleteCountryList()
    }
}