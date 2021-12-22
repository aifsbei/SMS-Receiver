package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search.countrylist

import androidx.recyclerview.widget.DiffUtil
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country

class SearchCountryDiffCallback : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }
}