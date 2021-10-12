package com.tmvlg.smsreceiver.presentation.rent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.data.network.onlinesim.OnlineSimApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RentNumberFragment : Fragment() {

    private lateinit var viewModel: RentNumberViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(RentNumberViewModel::class.java)

        viewModel.numberForRentList.observe(viewLifecycleOwner) {

        }

        val apiService = OnlineSimApiService()

        GlobalScope.launch(Dispatchers.Main) {
            val getNumResponse = apiService.getNum("Facebook").await()
            Log.d("1", "onCreateViewCoroutine: ${getNumResponse.tzid}")
        }

        return inflater.inflate(R.layout.fragment_rent_number_empty, container, false)
    }

}