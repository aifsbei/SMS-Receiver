package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentSearchCountryBinding
import java.lang.RuntimeException

class SearchCountryFragment : Fragment() {

    private var _binding: FragmentSearchCountryBinding? = null
    private val binding: FragmentSearchCountryBinding
        get() = _binding ?: throw RuntimeException("null binding at $this")

    private lateinit var viewModel: SearchCountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchCountryBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(SearchCountryViewModel::class.java)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SearchCountryFragment()
    }

}