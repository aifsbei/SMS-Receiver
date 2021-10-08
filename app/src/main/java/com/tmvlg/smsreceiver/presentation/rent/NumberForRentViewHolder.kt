package com.tmvlg.smsreceiver.presentation.rent

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.tmvlg.smsreceiver.R

class NumberForRentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var numberServiceIcon = itemView.findViewById<ImageView>(R.id.number_service_icon)
    var numberCallNumber = itemView.findViewById<TextView>(R.id.number_call_number)
    var numberRegion = itemView.findViewById<TextView>(R.id.number_region)
    var numberTimeLeft = itemView.findViewById<TextView>(R.id.number_time_left)
    var numberProgressTimeLeft = itemView.findViewById<LinearProgressIndicator>(R.id.number_progress_time_left)
}