package com.tmvlg.smsreceiver.presentation.freerent

import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.data.FreeNumbersParser
import com.tmvlg.smsreceiver.backend.RecyclerItemDecoration
import com.tmvlg.smsreceiver.backend.RecyclerItemDecoration.SectionCallback
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [FreeRentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FreeRentFragment : Fragment() {

    var TAG = "1"
    var parser: FreeNumbersParser? = null
    var dataList: ArrayList<HashMap<String, String>>? = null
    var free_recycle_view: RecyclerView? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var free_rent_search_entry: TextInputEditText? = null
    var shimmerFreeNumberLayout: ShimmerFrameLayout? = null
    var recyclerItemDecoration: RecyclerItemDecoration? = null

    private var INIT_VAR = 0


    private lateinit var viewModel: FreeRentViewModel
    private lateinit var freeNumberDataAdapter: FreeNumberDataAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_free_rent, container, false)
        dataList = ArrayList()
        free_recycle_view = view.findViewById(R.id.free_recycle_view)
        shimmerFreeNumberLayout = view.findViewById(R.id.shimmer_free_rent_layout)
        free_rent_search_entry = view.findViewById(R.id.free_rent_search_entry)
        linearLayoutManager = LinearLayoutManager(activity)
        free_recycle_view?.setLayoutManager(linearLayoutManager)
        parser = FreeNumbersParser()
//        AsyncParse().execute()
        ButterKnife.bind(view)
        free_rent_search_entry?.addTextChangedListener(search_text_changed_listener)

        viewModel = ViewModelProvider(this).get(FreeRentViewModel::class.java)

        viewModel.initRepository()
        shimmerFreeNumberLayout!!.visibility = View.GONE
        free_recycle_view!!.visibility = View.VISIBLE
        setupRecyclerView()
        viewModel.numberList.observe(viewLifecycleOwner) {
//            if (INIT_VAR == 0) {
//                Log.d(TAG, "onCreateView: INIT1")
//                setupRecyclerView()
//                INIT_VAR++
//            }
//            freeNumberDataAdapter.freeNumberList = it
            freeNumberDataAdapter.setList(it)
//            freeNumberDataAdapter.setList(it)
            Log.d(TAG, "onCreateView: INIT2")
        }


//        getData();
        return view
    }


    fun setupRecyclerView() {
        freeNumberDataAdapter = FreeNumberDataAdapter()
        free_recycle_view?.adapter = freeNumberDataAdapter


    }

    fun getSectionCallback(list: List<FreeNumber>): SectionCallback {
        return object : SectionCallback {
            override fun isSection(pos: Int): Boolean {
                return pos == 0 || list[pos].counrty_name != list[pos - 1].counrty_name
            }

            override fun getSectionHeaderName(pos: Int): String {
                val headerName = list.get(pos).counrty_name
                return headerName
            }
        }
    }

    var search_text_changed_listener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            filter(editable.toString())
        }
    }

    fun filter(text: String) {
        val filteredList = ArrayList<HashMap<String, String>>()
        for (num in parser!!.numbersList) {
            if (num.counrty_name.toLowerCase().contains(text.toLowerCase()) ||
                    num.country_code.toLowerCase().contains(text.toLowerCase()) ||
                    num.call_number.toLowerCase().contains(text.toLowerCase()) ||
                    num.call_number_prefix.toLowerCase().contains(text.toLowerCase())) {
                val dataMApi = HashMap<String, String>()
                dataMApi["Title"] = num.counrty_name
                val icon_name = "flag_" + num.country_code.toLowerCase()
                //                String icon_name = num.country_code.toLowerCase();
                dataMApi["free_region_icon"] = icon_name
                val prefix = num.country_code + " " + num.call_number_prefix
                dataMApi["free_prefix"] = prefix
                dataMApi["free_call_number"] = num.call_number
                dataMApi["origin"] = num.origin
                filteredList.add(dataMApi)
            }
        }
//        adapter!!.filterList(filteredList)


//        adapter = new FreeNumberDataAdapter(getActivity(), filteredList);
//        free_recycle_view.setAdapter(adapter);

        //TODO: BUG
//        recyclerItemDecoration!!.setSectionCallback(getSectionCallback(freeNumberDataAdapter.freeNumberList))

//        free_recycle_view.addItemDecoration(recyclerItemDecoration);
    }

//    internal inner class AsyncParse : AsyncTask<Void?, Void?, Void?>() {
//        override fun doInBackground(vararg params: Void?): Void? {
//            var numbers_data_list: ArrayList<ArrayList<String?>?>
//            try {
//                parser!!.parse_numbers()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: Void?) {
//            parser!!.print_data()
//            shimmerFreeNumberLayout!!.visibility = View.GONE
//            free_recycle_view!!.visibility = View.VISIBLE
//            for (num in parser!!.numbersList) {
//                val dataMApi = HashMap<String, String>()
//                dataMApi["Title"] = num.counrty_name
//                val icon_name = "flag_" + num.country_code.toLowerCase()
//                //                String icon_name = num.country_code.toLowerCase();
//                dataMApi["free_region_icon"] = icon_name
//                val prefix = num.country_code + " " + num.call_number_prefix
//                dataMApi["free_prefix"] = prefix
//                dataMApi["free_call_number"] = num.call_number
//                dataMApi["origin"] = num.origin
//                dataList!!.add(dataMApi)
//            }
//
////            getData();
//            adapter = FreeNumberDataAdapter()   //FIX ARGUMENTS HERE!!
//            free_recycle_view!!.adapter = adapter
//            recyclerItemDecoration = RecyclerItemDecoration(activity,
//                    resources.getDimensionPixelSize(R.dimen.free_header_height),
//                    true,
//                    getSectionCallback(dataList))
//            free_recycle_view!!.addItemDecoration(recyclerItemDecoration!!)
//            super.onPostExecute(result)
//        }
//    }
}
