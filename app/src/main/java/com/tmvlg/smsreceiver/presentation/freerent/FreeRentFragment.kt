package com.tmvlg.smsreceiver.presentation.freerent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentFreeRentBinding
import com.tmvlg.smsreceiver.presentation.freerent.freenumberlist.FreeNumberDataAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class FreeRentFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: FreeRentViewModel
    private lateinit var freeNumberDataAdapter: FreeNumberDataAdapter

    override val kodein by closestKodein()

    private val freeRentViewModelFactory by instance<FreeRentViewModelFactory>()

    private var _binding: FragmentFreeRentBinding? = null
    private val binding: FragmentFreeRentBinding
        get() = _binding ?: throw IllegalStateException("null binding at $this")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFreeRentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            freeRentViewModelFactory
        )[FreeRentViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.freeRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchEditText.addTextChangedListener {
            viewModel.filterFreeNumberList(it.toString())

        }

        binding.shimmerFreeRentLayout.visibility = View.VISIBLE
        binding.freeRecycleView.visibility = View.GONE

        viewModel.initRepository()

        setupRecyclerView()

        viewModel.numberList.observe(viewLifecycleOwner)
        {
            freeNumberDataAdapter.submitList(it)
            if (it.isNotEmpty()) {
                binding.shimmerFreeRentLayout.visibility = View.GONE
                binding.freeRecycleView.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearList()
        _binding = null
    }

    fun setupRecyclerView() {
        freeNumberDataAdapter = FreeNumberDataAdapter()
        binding.freeRecycleView.adapter = freeNumberDataAdapter

        freeNumberDataAdapter.onFreeNumberClickListener = {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in, R.anim.fragment_out)
                .add(R.id.container, FreeRentDetailFragment.newInstance(it.id))
                .addToBackStack("details")
                .commit()
        }
    }

    companion object {
        private const val TAG = "1"
    }

}

