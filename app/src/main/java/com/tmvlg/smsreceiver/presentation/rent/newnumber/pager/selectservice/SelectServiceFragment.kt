package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice

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
import com.tmvlg.smsreceiver.databinding.FragmentSelectServiceBinding
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search.SearchCountryFragment
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search.SearchCountryUiState
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search.countrylist.SearchCountryListAdapter
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.search.SearchServiceFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SelectServiceFragment : Fragment(R.layout.fragment_select_service), KodeinAware {

    override val kodein by closestKodein()

//    private val factory: SelectServiceViewModelFactory by instance()

//    private val viewModel: SelectServiceViewModel by viewModels { factory }

    private val binding by viewBinding<FragmentSelectServiceBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeCountryButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, SearchServiceFragment.newInstance())
                .commit()
        }

    }

    companion object {
        fun newInstance() = SelectServiceFragment()
    }
}