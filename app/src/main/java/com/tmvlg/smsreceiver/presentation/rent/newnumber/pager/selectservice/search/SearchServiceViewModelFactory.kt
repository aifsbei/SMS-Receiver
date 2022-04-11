package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.SelectServiceViewModel
import java.lang.RuntimeException

class SearchServiceViewModelFactory(
    private val repository: NumberForRentRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SelectServiceViewModel::class.java))
                return SearchServiceViewModel(repository) as T
            throw RuntimeException("Unknown view model class $modelClass")
        }
    }