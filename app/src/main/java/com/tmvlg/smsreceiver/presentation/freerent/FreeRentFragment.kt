package com.tmvlg.smsreceiver.presentation.freerent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.data.FreeNumbersParser
import com.tmvlg.smsreceiver.util.RecyclerItemDecoration
import com.tmvlg.smsreceiver.util.RecyclerItemDecoration.SectionCallback
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


    private lateinit var viewModel: FreeRentViewModel
    private lateinit var freeNumberDataAdapter: FreeNumberDataAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_free_rent, container, false)
        dataList = ArrayList()
        free_recycle_view = view.findViewById(R.id.free_recycle_view)
        shimmerFreeNumberLayout = view.findViewById(R.id.shimmer_free_rent_layout)
        free_rent_search_entry = view.findViewById(R.id.free_rent_search_entry)
        linearLayoutManager = LinearLayoutManager(activity)
        free_recycle_view?.setLayoutManager(linearLayoutManager)
        parser = FreeNumbersParser()
        free_rent_search_entry?.addTextChangedListener(search_text_changed_listener)

        viewModel = ViewModelProvider(this).get(FreeRentViewModel::class.java)

        viewModel.initRepository()
//        shimmerFreeNumberLayout!!.visibility = View.GONE
//        free_recycle_view!!.visibility = View.VISIBLE
        setupRecyclerView()
        viewModel.numberList.observe(viewLifecycleOwner) {

            freeNumberDataAdapter.submitList(it)
            shimmerFreeNumberLayout!!.visibility = View.GONE
            free_recycle_view!!.visibility = View.VISIBLE
            Log.d(TAG, "onCreateView: INIT2")
        }
        return view
    }


    fun setupRecyclerView() {
        freeNumberDataAdapter = FreeNumberDataAdapter()
        free_recycle_view?.adapter = freeNumberDataAdapter

        freeNumberDataAdapter.onFreeNumberClickListener = object : FreeNumberDataAdapter.OnFreeNumberClickListener {
            override fun onFreeNumberClick(freeNumber: FreeNumber) {
                Log.d(TAG, "onFreeNumberClick: clicked")
            }

        }
    }

    var search_text_changed_listener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            viewModel.filterFreeNumberList(editable.toString())
        }
    }
}

