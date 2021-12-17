package com.tmvlg.smsreceiver.presentation.freerent

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FreeMessageBinding
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessage
import java.util.*

class FreeMessageDataAdapter : ListAdapter<FreeMessage, RecyclerView.ViewHolder>(FreeMessageDiffCallback()) {

    var baseColor: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreeMessageViewHolder {
        Log.d("1", "problem: vh created")
        val inflater = LayoutInflater.from(parent.context)
        val binding = FreeMessageBinding.inflate(inflater, parent, false)
        return FreeMessageViewHolder(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val freeMessage = getItem(position)
        with(holder as FreeMessageViewHolder) {
            binding.freeMessageHeader.text = freeMessage.message_title
            binding.freeMessageText.text = freeMessage.message_text
            binding.freeTimeRemained.text = freeMessage.time_remained
            binding.freeMessageHeader.setTextColor(baseColor!!)

            val unwrappedDrawable = binding.freeTimeRemained.background
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable)
            DrawableCompat.setTint(wrappedDrawable, baseColor!!)
            binding.freeTimeRemained.setTextColor(baseColor!!)
        }
    }
}