package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentSearchCountryBinding
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search.countrylist.SearchCountryListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.RuntimeException

class SearchCountryFragment : Fragment(R.layout.fragment_search_country), KodeinAware {

    override val kodein by closestKodein()

    private val binding: FragmentSearchCountryBinding by viewBinding()

    private lateinit var countryAdapter: SearchCountryListAdapter

    private val factory: SearchCountryViewModelFactory by instance()

    private val viewModel by viewModels<SearchCountryViewModel> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryRv.layoutManager = LinearLayoutManager(requireContext())
        countryAdapter = SearchCountryListAdapter()
        binding.countryRv.adapter = countryAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is SearchCountryUiState.Idle -> {
                            viewModel.loadCountries()
                        }
                        is SearchCountryUiState.Progress -> {
                            // TODO: сюда поставить шиммер
                        }
                        is SearchCountryUiState.Success -> {
                            countryAdapter.submitList(uiState.countryList)
                        }
                        is SearchCountryUiState.Error -> {
                            // TODO: попросить пользователя сделать релоад 
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = SearchCountryFragment()
    }

}