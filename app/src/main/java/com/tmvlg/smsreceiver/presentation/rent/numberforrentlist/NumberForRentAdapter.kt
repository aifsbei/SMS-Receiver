package com.tmvlg.smsreceiver.presentation.rent.numberforrentlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.NumberForRentBinding
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRent

class NumberForRentAdapter : ListAdapter<NumberForRent, NumberForRentViewHolder>(
    NumberForRentDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberForRentViewHolder {
        val layout = R.layout.number_for_rent
        val inflater = LayoutInflater.from(parent.context)
        val view = LayoutInflater.from(parent.context).inflate(layout,
            parent,
            false)
        val binding = NumberForRentBinding.inflate(inflater, parent, false)
        return NumberForRentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumberForRentViewHolder, position: Int) {
        val number = getItem(position)
        with (holder) {
            binding.numberCallNumber.text = number.call_number
            binding.numberRegion.text = number.country_code
            binding.numberTimeLeft.text = number.time_left.toString()
        }
    }

}