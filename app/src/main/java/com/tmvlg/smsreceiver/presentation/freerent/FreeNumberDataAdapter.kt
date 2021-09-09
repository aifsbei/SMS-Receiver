package com.tmvlg.smsreceiver.presentation.freerent

import StickyHeaderItemDecoration
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.backend.RecyclerItemDecoration
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import java.io.InputStream
import java.lang.RuntimeException


class FreeNumberDataAdapter : RecyclerView.Adapter<FreeNumberDataAdapter.DataHolder>() {
    var freeNumberList = listOf<FreeNumber>()
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val layout = when (viewType) {
            VIEWTYPE_HEADER -> R.layout.free_number_header
            VIEWTYPE_ITEM -> R.layout.free_number
            else -> throw RuntimeException()
        }
        val view = LayoutInflater.from(parent.context).inflate(layout,
                parent,
                false)
        context = parent.context!!
        return DataHolder(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val number = freeNumberList[position]

        when (number.type) {
            FreeNumber.ITEM_TYPE -> {
                //        String icon_path = dataMap.get("free_region_icon");
//        Log.d("2", icon_path);
//        holder.free_region_icon.setImageResource(resID);
                val icon_path = number.icon_path.lowercase()
                val resID = context?.getResources()?.getIdentifier(icon_path, "xml", context?.packageName);

                Log.d("1", icon_path)
//        val inputStream: InputStream = javaClass.getResourceAsStream("/xml/$icon_path")!!
//        holder.free_region_icon.setImageDrawable(Drawable.createFromStream(inputStream, ""))

                //TODO: ПЕРЕСТАЛО РАБОТАТЬ
//        val resID = getResId(icon_path, xml::class.java)
                holder.free_region_icon.setImageResource(resID!!)

//        String icon_path = dataMap.get("free_region_icon");
//        Log.d("2", icon_path);
//        InputStream is = context.getResources().openRawResource(
//                context.getResources().getIdentifier(icon_path,
//                        "xml", context.getPackageName()));
//        Bitmap bitmap = BitmapFactory.decodeStream(is);
//        holder.free_region_icon.setImageBitmap(bitmap);
                holder.free_prefix.text = number.call_number_prefix
                holder.free_call_number.text = number.call_number
                holder.free_number_layout.setOnClickListener {
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
                holder.country_name_shimmer.text = number.counrty_name
            }
        }


    }


    override fun getItemCount(): Int {
        return freeNumberList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = freeNumberList[position]
        return when (item.type) {
            FreeNumber.HEADER_TYPE -> VIEWTYPE_HEADER
            FreeNumber.ITEM_TYPE -> VIEWTYPE_ITEM
            else -> throw RuntimeException("Unknown item ViewType at $position position")
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
//        recyclerView.addItemDecoration(RecyclerItemDecoration(context,
//                    context?.resources!!.getDimensionPixelSize(R.dimen.free_header_height),
//                    true,
//                    context?.getSectionCallback(freeNumberList)))
        recyclerView.addItemDecoration(StickyHeaderItemDecoration(R.layout.free_number_header, VIEWTYPE_HEADER))
    }


    inner class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //free_number.xml
        var free_region_icon = itemView.findViewById<ImageView>(R.id.free_region_icon_shimmer)
        var free_prefix = itemView.findViewById<TextView>(R.id.free_prefix_shimmer)
        var free_call_number = itemView.findViewById<TextView>(R.id.free_call_number_shimmer)
        var free_number_layout = itemView.findViewById<LinearLayout>(R.id.free_number_layout_shimmer)

        //free_number_header.xml
        var country_name_shimmer = itemView.findViewById<TextView>(R.id.country_name_shimmer)
    }

    fun filterList(filteredList: List<FreeNumber>) {
        freeNumberList = filteredList
        notifyDataSetChanged()
    }

    fun setList(list: List<FreeNumber>) {
        freeNumberList = list
        notifyDataSetChanged()
    }

    companion object {

        const val VIEWTYPE_HEADER = 100
        const val VIEWTYPE_ITEM = 101

        @JvmStatic
        fun getResId(resName: String?, c: Class<*>): Int {
            return try {
                val idField = c.getDeclaredField(resName!!)
                idField.getInt(idField)
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }
    }


}