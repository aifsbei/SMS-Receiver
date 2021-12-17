package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.payfornumber

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tmvlg.smsreceiver.R

class PayForNumberFragment : Fragment() {

    companion object {
        fun newInstance() = PayForNumberFragment()
    }

    private lateinit var viewModel: PayForNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay_for_number, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PayForNumberViewModel::class.java)
        // TODO: Use the ViewModel
    }

}