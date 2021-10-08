package com.tmvlg.smsreceiver.presentation.rent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRent

class NumberForRentAdapter : ListAdapter<NumberForRent, NumberForRentViewHolder>(NumberForRentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberForRentViewHolder {
        val layout = R.layout.number_for_rent
        val view = LayoutInflater.from(parent.context).inflate(layout,
            parent,
            false)
        return NumberForRentViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumberForRentViewHolder, position: Int) {
        val number = getItem(position)
        with (holder) {
            numberCallNumber.text = number.call_number
            numberRegion.text = number.country_code
            numberTimeLeft.text = number.time_left.toString()
        }
    }

}