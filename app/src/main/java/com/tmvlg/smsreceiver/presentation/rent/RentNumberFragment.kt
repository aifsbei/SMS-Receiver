package com.tmvlg.smsreceiver.presentation.rent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.R


class RentNumberFragment : Fragment() {

    private lateinit var viewModel: RentNumberViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(RentNumberViewModel::class.java)

        viewModel.numberForRentList.observe(viewLifecycleOwner) {

        }

        return inflater.inflate(R.layout.fragment_rent_number_empty, container, false)
    }

}