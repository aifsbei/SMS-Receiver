package com.tmvlg.smsreceiver.presentation.freerent

import androidx.recyclerview.widget.DiffUtil
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessage

class FreeMessageDiffCallback : DiffUtil.ItemCallback<FreeMessage>() {

    override fun areItemsTheSame(oldItem: FreeMessage, newItem: FreeMessage): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FreeMessage, newItem: FreeMessage): Boolean {
        return oldItem == newItem
    }
}