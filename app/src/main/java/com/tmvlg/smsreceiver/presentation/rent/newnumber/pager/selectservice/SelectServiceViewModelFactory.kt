package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import java.lang.RuntimeException

class SelectServiceViewModelFactory(
    private val repository: NumberForRentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectServiceViewModel::class.java))
            return SelectServiceViewModel(repository) as T
        throw RuntimeException("Unknown view model class $modelClass")
    }
}