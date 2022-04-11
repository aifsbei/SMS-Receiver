package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentSearchServiceBinding
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.search.servicelist.SearchServiceListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SearchServiceFragment : Fragment(R.layout.fragment_search_service), KodeinAware {
    override val kodein by closestKodein()

    private val factory: SearchServiceViewModelFactory by instance()

    private val viewModel: SearchServiceViewModel by viewModels { factory }

    private val binding by viewBinding<FragmentSearchServiceBinding>()

    private lateinit var serviceAdapter: SearchServiceListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.serviceRv.layoutManager = LinearLayoutManager(requireContext())
        serviceAdapter = SearchServiceListAdapter()
        binding.serviceRv.adapter = serviceAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is SearchServiceUiState.Idle -> {
                            viewModel.loadCountries()
                        }
                        is SearchServiceUiState.Progress -> {
                            // TODO: сюда поставить шиммер
                        }
                        is SearchServiceUiState.Success -> {
                            serviceAdapter.submitList(uiState.serviceList)
                        }
                        is SearchServiceUiState.Error -> {
                            // TODO: попросить пользователя сделать релоад
                        }
                    }
                }
            }
        }
    }
    
    companion object {
        fun newInstance() = SearchServiceFragment()
    }
}