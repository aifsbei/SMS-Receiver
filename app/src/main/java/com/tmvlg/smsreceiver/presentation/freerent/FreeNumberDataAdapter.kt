package com.tmvlg.smsreceiver.presentation.freerent

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FreeNumberBinding
import com.tmvlg.smsreceiver.databinding.FreeNumberHeaderBinding
import com.tmvlg.smsreceiver.util.ViewHolderStickyDecoration
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber


class FreeNumberDataAdapter : ListAdapter<FreeNumber, RecyclerView.ViewHolder>(FreeNumberDiffCallback()), ViewHolderStickyDecoration.Condition {

    var context: Context? = null
    var onFreeNumberClickListener: ((FreeNumber) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            VIEWTYPE_HEADER -> FreeNumberHeaderBinding.inflate(inflater, parent, false)
            VIEWTYPE_ITEM -> FreeNumberBinding.inflate(inflater, parent, false)
            else -> throw RuntimeException()
        }

        context = parent.context!!
        return when (viewType) {
            VIEWTYPE_HEADER -> FreeNumberHeaderHolder(binding as FreeNumberHeaderBinding)
            VIEWTYPE_ITEM -> FreeNumberItemHolder(binding as FreeNumberBinding)
            else -> throw RuntimeException()
        }
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val number = getItem(position)

        when (number.type) {
            FreeNumber.ITEM_TYPE -> {
                val icon_path = number.icon_path.lowercase()
                val resID = context?.getResources()?.getIdentifier("flag_" + icon_path.substring(0, 2), "xml", context?.packageName)

                Log.d("1", icon_path)

                with (holder as FreeNumberItemHolder) {
                    binding.freeRegionIcon.setImageResource(resID!!)
//                Picasso.get().load(resID!!).into((holder as FreeNumberItemHolder).free_region_icon)
                    binding.freePrefix.text = number.call_number_prefix
                    binding.freeCallNumber.text = number.call_number
                    binding.freeNumberLayout.setOnClickListener {
                        onFreeNumberClickListener?.invoke(number)
                    }
                }

            }
            FreeNumber.HEADER_TYPE -> {
                with (holder as FreeNumberHeaderHolder) {
                    binding.countryName.text = number.counrty_name
                }
            }
        }


    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.type) {
            FreeNumber.HEADER_TYPE -> VIEWTYPE_HEADER
            FreeNumber.ITEM_TYPE -> VIEWTYPE_ITEM
            else -> throw RuntimeException("Unknown item ViewType at $position position")
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(ViewHolderStickyDecoration(recyclerView, this))
    }

    override fun isHeader(position: Int): Boolean {
        try {
            return getItem(position).type == FreeNumber.HEADER_TYPE
        } catch (e: ArrayIndexOutOfBoundsException) {
            return false
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    companion object {

        const val VIEWTYPE_HEADER = 100
        const val VIEWTYPE_ITEM = 101

    }

}