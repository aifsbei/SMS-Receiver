package com.tmvlg.smsreceiver.presentation.freerent

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shuhart.stickyheader.StickyAdapter
import com.shuhart.stickyheader.StickyHeaderItemDecorator
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.backend.RecyclerItemDecoration
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber


class FreeNumberDataAdapter : StickyAdapter<RecyclerView.ViewHolder, RecyclerView.ViewHolder>() {
    var freeNumberList = listOf<FreeNumber>()
    var context: Context? = null
    var SECTION_POSITION = -1

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
            VIEWTYPE_HEADER -> HeaderViewholder(view)
            VIEWTYPE_ITEM -> DataHolder(view)
            else -> throw RuntimeException()
        }
        return DataHolder(view)

    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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
                (holder as DataHolder).free_region_icon.setImageResource(resID!!)

//        String icon_path = dataMap.get("free_region_icon");
//        Log.d("2", icon_path);
//        InputStream is = context.getResources().openRawResource(
//                context.getResources().getIdentifier(icon_path,
//                        "xml", context.getPackageName()));
//        Bitmap bitmap = BitmapFactory.decodeStream(is);
//        holder.free_region_icon.setImageBitmap(bitmap);
                (holder as DataHolder).free_prefix.text = number.call_number_prefix
                (holder as DataHolder).free_call_number.text = number.call_number
                (holder as DataHolder).free_number_layout.setOnClickListener {
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
                (holder as HeaderViewholder).country_name_shimmer.text = number.counrty_name
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
//                    getSectionCallback(freeNumberList)))
//        recyclerView.addItemDecoration(StickyHeaderItemDecoration(R.layout.free_number_header, VIEWTYPE_HEADER))
        val decorator = StickyHeaderItemDecorator(this)
        decorator.attachToRecyclerView(recyclerView)
    }

    fun getSectionCallback(list: List<FreeNumber>): RecyclerItemDecoration.SectionCallback {
        return object : RecyclerItemDecoration.SectionCallback {
            override fun isSection(pos: Int): Boolean {
                return pos == 0 || list[pos].type != list[pos - 1].type
            }

            override fun getSectionHeaderName(pos: Int): String {
                val headerName = list.get(pos).counrty_name
                return headerName
            }
        }
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


    class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //free_number.xml
        var free_region_icon = itemView.findViewById<ImageView>(R.id.free_region_icon_shimmer)
        var free_prefix = itemView.findViewById<TextView>(R.id.free_prefix_shimmer)
        var free_call_number = itemView.findViewById<TextView>(R.id.free_call_number_shimmer)
        var free_number_layout = itemView.findViewById<LinearLayout>(R.id.free_number_layout_shimmer)

        //free_number_header.xml
        var country_name_shimmer = itemView.findViewById<TextView>(R.id.country_name_shimmer)
    }

    class HeaderViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var country_name_shimmer = itemView.findViewById<TextView>(R.id.country_name_shimmer)
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        if (freeNumberList[itemPosition].type == FreeNumber.HEADER_TYPE) {
            SECTION_POSITION = itemPosition
        }

        return SECTION_POSITION
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?, headerPosition: Int) {
        (holder as HeaderViewholder).country_name_shimmer.text = freeNumberList[headerPosition].counrty_name
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return createViewHolder(parent, VIEWTYPE_HEADER)
    }


}