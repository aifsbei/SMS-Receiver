package com.tmvlg.smsreceiver.presentation.freerent

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R

class FreeNumberItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //free_number.xml
    var free_region_icon = itemView.findViewById<ImageView>(R.id.free_region_icon)
    var free_prefix = itemView.findViewById<TextView>(R.id.free_prefix)
    var free_call_number = itemView.findViewById<TextView>(R.id.free_call_number)
    var free_number_layout = itemView.findViewById<LinearLayout>(R.id.free_number_layout)

    //free_number_header.xml
    var country_name_shimmer = itemView.findViewById<TextView>(R.id.country_name)
}