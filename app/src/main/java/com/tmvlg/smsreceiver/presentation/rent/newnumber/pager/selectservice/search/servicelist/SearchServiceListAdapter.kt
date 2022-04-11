package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.search.servicelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.tmvlg.smsreceiver.databinding.ItemServiceBinding
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Service

class SearchServiceListAdapter : ListAdapter<Service, SearchServiceViewHolder>(SearchServiceDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchServiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemServiceBinding.inflate(inflater, parent, false)
        return SearchServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchServiceViewHolder, position: Int) {
        val service = getItem(position)

        with(holder) {
            // TODO: биндим сервис
        }
    }

}