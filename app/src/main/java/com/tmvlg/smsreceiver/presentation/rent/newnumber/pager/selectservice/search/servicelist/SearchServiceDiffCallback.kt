package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.search.servicelist

import androidx.recyclerview.widget.DiffUtil
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Service

class SearchServiceDiffCallback : DiffUtil.ItemCallback<Service>() {
    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem == newItem
    }
}