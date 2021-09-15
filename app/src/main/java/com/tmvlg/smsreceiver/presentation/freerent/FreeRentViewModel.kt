package com.tmvlg.smsreceiver.presentation.freerent

import android.app.Application
import android.os.AsyncTask
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.backend.RecyclerItemDecoration
import com.tmvlg.smsreceiver.data.freenumber.FreeNumberRepositoryImpl
import com.tmvlg.smsreceiver.domain.freenumber.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.HashMap

class FreeRentViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application

    private var repository:  FreeNumberRepository = FreeNumberRepositoryImpl

    private val addFreeNumberUseCase = AddFreeNumberUseCase(repository)
    private val getFreeNumberUseCase = GetFreeNumberUseCase(repository)
    private val getFreeNumberListUseCase = GetFreeNumberListUseCase(repository)
    private val loadFreeNumberListUseCase = LoadFreeNumberListUseCase(repository)

    val numberList = getFreeNumberListUseCase.getFreeNumberList()

//    fun getFreeNumberList() {
//        getFreeNumberListTask().execute()
//    }

    fun initRepository() {
//        loadFreeNumberListUseCase.loadFreeNumberList()
        viewModelScope.launch(Dispatchers.IO) {
            loadFreeNumberListUseCase.loadFreeNumberList()
        }
    }

//    private inner class getFreeNumberListTask : AsyncTask<Void?, Void?, Void?>() {
//        override fun doInBackground(vararg params: Void?): Void? {
//            try {
//                repository = FreeNumberRepositoryImpl
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: Void?) {
////            mContext.shimmerFreeNumberLayout!!.visibility = View.GONE
////            free_recycle_view!!.visibility = View.VISIBLE
//
//
////            for (num in parser!!.numbersList) {
////                val dataMApi = HashMap<String, String>()
////                dataMApi["Title"] = num.counrty_name
////                val icon_name = "flag_" + num.country_code.toLowerCase()
////                //                String icon_name = num.country_code.toLowerCase();
////                dataMApi["free_region_icon"] = icon_name
////                val prefix = num.country_code + " " + num.call_number_prefix
////                dataMApi["free_prefix"] = prefix
////                dataMApi["free_call_number"] = num.call_number
////                dataMApi["origin"] = num.origin
////                dataList!!.add(dataMApi)
////            }
//
//
//
////            getData();
//            val adapter = FreeNumberDataAdapter()   //FIX ARGUMENTS HERE!!
//            free_recycle_view!!.adapter = adapter
//            recyclerItemDecoration = RecyclerItemDecoration(activity,
//                    resources.getDimensionPixelSize(R.dimen.free_header_height),
//                    true,
//                    getSectionCallback(dataList))
//            free_recycle_view!!.addItemDecoration(recyclerItemDecoration!!)
//            super.onPostExecute(result)
//        }
//    }

}