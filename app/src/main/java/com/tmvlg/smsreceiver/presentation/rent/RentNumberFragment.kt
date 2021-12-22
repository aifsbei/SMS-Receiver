package com.tmvlg.smsreceiver.presentation.rent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentFreeRentDetailBinding
import com.tmvlg.smsreceiver.databinding.FragmentRentNumberBinding
import com.tmvlg.smsreceiver.presentation.rent.newnumber.NewNumberFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class   RentNumberFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var viewModel: RentNumberViewModel

    private var _binding: FragmentRentNumberBinding? = null
    private val binding: FragmentRentNumberBinding
        get() = _binding ?: throw IllegalStateException("null binding at $this")

    private val factory: RentNumberViewModelFactory by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        viewModel = ViewModelProvider(
            this,
            factory
        )[RentNumberViewModel::class.java]

        _binding = FragmentRentNumberBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val apiService = OnlineSimApiService()
//
//        GlobalScope.launch(Dispatchers.Main) {
//            val getNumResponse = apiService.getNum("Facebook").await()
//            Log.d("1", "onCreateViewCoroutine: ${getNumResponse.tzid}")
//        }

        binding.addNumberFab.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_in, R.anim.fade_out, R.anim.fade_in, R.anim.fragment_out)
                .replace(R.id.container, NewNumberFragment.newInstance())
                .addToBackStack("new_number")
                .commit()
        }
        Log.d(TAG, "onViewCreated: created $this")
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.numberForRentList.observe(viewLifecycleOwner) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onViewCreated: destroed $this")
        _binding = null
    }

    companion object {
        const val TAG = "1"
    }

}