package com.tmvlg.smsreceiver.backend

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.R.xml
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentInfoActivity
import java.util.*

class FreeNumberDataAdapter: RecyclerView.Adapter<FreeNumberDataAdapter.DataHolder>() {
    var list = ArrayList<HashMap<String, String>>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.free_number, parent, false)
        return DataHolder(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val dataMap = list[position]

//        String icon_path = dataMap.get("free_region_icon");
//        Log.d("2", icon_path);
//        int resID = context.getResources().getIdentifier(icon_path, "xml", context.getPackageResourcePath());
//        holder.free_region_icon.setImageResource(resID);
        val icon_path = dataMap["free_region_icon"]
        Log.d("1", icon_path!!)
        val resID = getResId(icon_path, xml::class.java)
        holder.free_region_icon.setImageResource(resID)

//        String icon_path = dataMap.get("free_region_icon");
//        Log.d("2", icon_path);
//        InputStream is = context.getResources().openRawResource(
//                context.getResources().getIdentifier(icon_path,
//                        "xml", context.getPackageName()));
//        Bitmap bitmap = BitmapFactory.decodeStream(is);
//        holder.free_region_icon.setImageBitmap(bitmap);
        holder.free_prefix.text = dataMap["free_prefix"]
        holder.free_call_number.text = dataMap["free_call_number"]
        holder.free_number_layout.setOnClickListener {
            val intent = Intent(holder.itemView.context, FreeRentInfoActivity::class.java)
            intent.putExtra("free_region_icon", dataMap["free_region_icon"])
            intent.putExtra("free_prefix", dataMap["free_prefix"])
            intent.putExtra("free_call_number", dataMap["free_call_number"])
            intent.putExtra("origin", dataMap["origin"])
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation((holder.itemView.context as Activity), holder.free_region_icon, "free_flag_transition")
            holder.itemView.context.startActivity(intent, options.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var free_region_icon: ImageView
        var free_prefix: TextView
        var free_call_number: TextView
        var free_number_layout: LinearLayout

        init {
            free_region_icon = itemView.findViewById<View>(R.id.free_region_icon_shimmer) as ImageView
            free_prefix = itemView.findViewById<View>(R.id.free_prefix_shimmer) as TextView
            free_call_number = itemView.findViewById<View>(R.id.free_call_number_shimmer) as TextView
            free_number_layout = itemView.findViewById<View>(R.id.free_number_layout_shimmer) as LinearLayout
        }
    }

    fun filterList(filteredList: ArrayList<HashMap<String, String>>) {
        list = filteredList
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun getResId(resName: String?, c: Class<*>): Int {
            return try {
                val idField = c.getDeclaredField(resName)
                idField.getInt(idField)
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }
    }


}