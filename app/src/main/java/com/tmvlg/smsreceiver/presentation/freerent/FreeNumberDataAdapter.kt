package com.tmvlg.smsreceiver.presentation.freerent

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.util.ViewHolderStickyDecoration
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber


class FreeNumberDataAdapter : ListAdapter<FreeNumber, RecyclerView.ViewHolder>(FreeNumberDiffCallback()), ViewHolderStickyDecoration.Condition {

    var context: Context? = null
    var onFreeNumberClickListener : OnFreeNumberClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = when (viewType) {
            VIEWTYPE_HEADER -> R.layout.free_number_header
            VIEWTYPE_ITEM -> R.layout.free_number
            else -> throw RuntimeException()
        }
        val view = LayoutInflater.from(parent.context).inflate(layout,
                parent,
                false)
        context = parent.context!!
        return when(viewType) {
            VIEWTYPE_HEADER -> FreeNumberHeaderHolder(view)
            VIEWTYPE_ITEM -> FreeNumberItemHolder(view)
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

                (holder as FreeNumberItemHolder).free_region_icon.setImageResource(resID!!)
//                Picasso.get().load(resID!!).into((holder as FreeNumberItemHolder).free_region_icon)


                (holder as FreeNumberItemHolder).free_prefix.text = number.call_number_prefix
                (holder as FreeNumberItemHolder).free_call_number.text = number.call_number
                (holder as FreeNumberItemHolder).free_number_layout.setOnClickListener {
                    onFreeNumberClickListener?.onFreeNumberClick(number)
//            val intent = Intent(holder.itemView.context, FreeRentInfoActivity::class.java)
//            intent.putExtra("free_region_icon", dataMap["free_region_icon"])
//            intent.putExtra("free_prefix", dataMap["free_prefix"])
//            intent.putExtra("free_call_number", dataMap["free_call_number"])
//            intent.putExtra("origin", dataMap["origin"])
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation((holder.itemView.context as Activity), holder.free_region_icon, "free_flag_transition")
//            holder.itemView.context.startActivity(intent, options.toBundle())
                }

                }
            FreeNumber.HEADER_TYPE -> {
                (holder as FreeNumberHeaderHolder).country_name.text = number.counrty_name
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
        }
    }

    interface OnFreeNumberClickListener {

        fun onFreeNumberClick(freeNumber: FreeNumber)
    }


    companion object {

        const val VIEWTYPE_HEADER = 100
        const val VIEWTYPE_ITEM = 101

    }

}