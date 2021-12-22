package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentSearchCountryBinding
import com.tmvlg.smsreceiver.presentation.rent.RentNumberViewModelFactory
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search.countrylist.SearchCountryListAdapter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.RuntimeException

class SearchCountryFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private var _binding: FragmentSearchCountryBinding? = null
    private val binding: FragmentSearchCountryBinding
        get() = _binding ?: throw RuntimeException("null binding at $this")

    private lateinit var viewModel: SearchCountryViewModel

    private lateinit var countryAdapter: SearchCountryListAdapter

    private val factory: SearchCountryViewModelFactory by instance()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchCountryBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, factory)[SearchCountryViewModel::class.java]


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryRv.layoutManager = LinearLayoutManager(requireContext())
        countryAdapter = SearchCountryListAdapter()
        binding.countryRv.adapter = countryAdapter

        viewModel.loadCountries()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countryList.observe(viewLifecycleOwner) {
            countryAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearCountryList()
        _binding = null
    }

    companion object {
        fun newInstance() = SearchCountryFragment()
    }

}