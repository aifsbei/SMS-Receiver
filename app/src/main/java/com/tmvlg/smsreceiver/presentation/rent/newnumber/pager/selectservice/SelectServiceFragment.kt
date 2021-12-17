package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tmvlg.smsreceiver.R

class SelectServiceFragment : Fragment() {

    companion object {
        fun newInstance() = SelectServiceFragment()
    }

    private lateinit var viewModel: SelectServiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_service, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelectServiceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}