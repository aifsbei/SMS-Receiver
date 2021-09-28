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
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessage
import java.util.*

class FreeMessageDataAdapter : ListAdapter<FreeMessage, RecyclerView.ViewHolder>(FreeMessageDiffCallback()) {

    var context: Context? = null

    var baseColor: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreeMessageViewHolder {
        Log.d("1", "problem: vh created")
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.free_message, parent, false)
        return FreeMessageViewHolder(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val freeMessage = getItem(position)
        with(holder as FreeMessageViewHolder) {
            Log.d("1", "problem: ${freeMessage.message_title})")
            free_message_header.text = freeMessage.message_title
            free_message_text.text = freeMessage.message_text
            free_time_remained.text = freeMessage.time_remained
            free_message_header.setTextColor(baseColor!!)

            val unwrappedDrawable = holder.free_time_remained.background
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable)
            DrawableCompat.setTint(wrappedDrawable, baseColor!!)
            free_time_remained.setTextColor(baseColor!!)
        }
    }
}