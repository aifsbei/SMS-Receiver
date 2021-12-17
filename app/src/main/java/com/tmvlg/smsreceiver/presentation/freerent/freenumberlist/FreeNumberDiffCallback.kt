package com.tmvlg.smsreceiver.presentation.freerent.freenumberlist

import androidx.recyclerview.widget.DiffUtil
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber

class FreeNumberDiffCallback : DiffUtil.ItemCallback<FreeNumber>() {

    override fun areItemsTheSame(oldItem: FreeNumber, newItem: FreeNumber): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FreeNumber, newItem: FreeNumber): Boolean {
        return oldItem == newItem
    }

}