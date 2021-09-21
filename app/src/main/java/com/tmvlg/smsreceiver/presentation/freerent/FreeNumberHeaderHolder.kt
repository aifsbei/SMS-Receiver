package com.tmvlg.smsreceiver.presentation.freerent

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R

class FreeNumberHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var country_name = itemView.findViewById<TextView>(R.id.country_name)
}