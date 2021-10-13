package com.tmvlg.smsreceiver.presentation.freerent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmvlg.smsreceiver.domain.freenumber.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FreeRentViewModel(
    private val repository: FreeNumberRepository
): ViewModel() {

    private val addFreeNumberUseCase = AddFreeNumberUseCase(repository)
    private val getFreeNumberUseCase = GetFreeNumberUseCase(repository)
    private val getFreeNumberListUseCase = GetFreeNumberListUseCase(repository)
    private val loadFreeNumberListUseCase = LoadFreeNumberListUseCase(repository)
    private val filterFreeNumberListUseCase = FilterFreeNumberListUseCase(repository)
    private val deleteFreeNumberListUseCase = DeleteFreeNumberListUseCase(repository)

    var numberList = getFreeNumberListUseCase.getFreeNumberList()

    fun initRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("1", "ok: COROUTINE STARTED")
            loadFreeNumberListUseCase.loadFreeNumberList()
            Log.d("1", "ok: COROUTINE ENDED")
            Log.d("1", "ok: ${numberList.value}")
        }
    }

    fun filterFreeNumberList(query: String) {
        filterFreeNumberListUseCase.filterFreeNumberListUseCase(query)
    }

    fun clearList() {
        deleteFreeNumberListUseCase.deleteFreeNumberList()
    }

}