package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentSelectCountryBinding
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.adapter.NewNumberPagerAdapter
import java.lang.RuntimeException

class SelectCountryFragment : Fragment() {

    private var _binding: FragmentSelectCountryBinding? = null
    private val binding: FragmentSelectCountryBinding
        get() = _binding ?: throw RuntimeException("null binding at $this")

    private lateinit var viewModel: SelectCountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSelectCountryBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(SelectCountryViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance() = SelectCountryFragment()
    }

}