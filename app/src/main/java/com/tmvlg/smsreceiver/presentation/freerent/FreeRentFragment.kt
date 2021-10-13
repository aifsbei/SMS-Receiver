package com.tmvlg.smsreceiver.presentation.freerent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.data.FreeNumbersParser
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [FreeRentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FreeRentFragment : Fragment(), KodeinAware {

    var TAG = "1"
    var free_recycler_view: RecyclerView? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var free_rent_search_entry: TextInputEditText? = null
    var shimmerFreeNumberLayout: ShimmerFrameLayout? = null
    var free_region_icon: ImageView? = null


    private lateinit var viewModel: FreeRentViewModel
    private lateinit var freeNumberDataAdapter: FreeNumberDataAdapter

    override val kodein by closestKodein()

    private val freeRentViewModelFactory by instance<FreeRentViewModelFactory>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_free_rent, container, false)
        free_recycler_view = view.findViewById(R.id.free_recycle_view)
        shimmerFreeNumberLayout = view.findViewById(R.id.shimmer_free_rent_layout)
        free_rent_search_entry = view.findViewById(R.id.free_rent_search_entry)
        free_region_icon = view.findViewById(R.id.free_region_icon)
        linearLayoutManager = LinearLayoutManager(activity)
        free_recycler_view?.setLayoutManager(linearLayoutManager)
        free_rent_search_entry?.addTextChangedListener(search_text_changed_listener)

        Log.d(TAG, "onCreateView: CREATE VIEW")
        shimmerFreeNumberLayout!!.visibility = View.VISIBLE
        free_recycler_view!!.visibility = View.GONE

        viewModel = ViewModelProvider(
            this,
            freeRentViewModelFactory
        ).get(FreeRentViewModel::class.java)

        viewModel.initRepository()

        setupRecyclerView()
        viewModel.numberList.observe(viewLifecycleOwner) {
            Log.d("1", "ok: INITED")

            freeNumberDataAdapter.submitList(it)
            shimmerFreeNumberLayout!!.visibility = View.GONE
            free_recycler_view!!.visibility = View.VISIBLE
            Log.d(TAG, "onCreateView: INIT2")
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearList()
    }

    fun setupRecyclerView() {
        freeNumberDataAdapter = FreeNumberDataAdapter()
        free_recycler_view?.adapter = freeNumberDataAdapter

        freeNumberDataAdapter.onFreeNumberClickListener = {
            val intent = FreeRentDetailActivity.newIntentShowDetail(requireContext(), it.id)
            startActivity(intent)
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

