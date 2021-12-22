package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.SearchCountryRepository
import java.lang.RuntimeException

class SearchCountryViewModelFactory(
    private val countryRepository: SearchCountryRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCountryViewModel::class.java))
            return SearchCountryViewModel(countryRepository) as T
        throw RuntimeException("Unknown view model class $modelClass")
    }
}