package com.tmvlg.smsreceiver.presentation.rent

import androidx.recyclerview.widget.DiffUtil
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRent

class NumberForRentDiffCallback: DiffUtil.ItemCallback<NumberForRent>() {

    override fun areItemsTheSame(oldItem: NumberForRent, newItem: NumberForRent): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NumberForRent, newItem: NumberForRent): Boolean {
        return oldItem == newItem
    }
}