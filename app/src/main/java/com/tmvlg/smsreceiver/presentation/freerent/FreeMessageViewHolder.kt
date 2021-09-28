package com.tmvlg.smsreceiver.presentation.freerent

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R

class FreeMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var free_message_header = itemView.findViewById<TextView>(R.id.free_message_header)
    var free_message_text = itemView.findViewById<TextView>(R.id.free_message_text)
    var free_time_remained = itemView.findViewById<TextView>(R.id.free_time_remained)
}