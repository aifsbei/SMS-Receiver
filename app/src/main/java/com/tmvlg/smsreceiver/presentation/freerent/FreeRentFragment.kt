package com.tmvlg.smsreceiver.presentation.freerent

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentFreeRentBinding
import com.tmvlg.smsreceiver.presentation.freerent.freenumberlist.FreeNumberDataAdapter
import com.tmvlg.smsreceiver.util.isOnline
import kotlinx.coroutines.*
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

    private var interstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(TAG, "task: start fragment")
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

//        binding.shimmerFreeRentLayout.visibility = View.VISIBLE
        binding.freeRecycleView.visibility = View.GONE

        if (requireContext().isOnline()) {
            Log.d(TAG, "task: init repository started $this")
            binding.shimmerFreeRentLayout.visibility = View.VISIBLE
            viewModel.initRepository()
        }
        else {
            binding.noConnectionLayout.visibility = View.VISIBLE
            binding.shimmerFreeRentLayout.visibility = View.INVISIBLE
        }

        binding.reloadButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, FreeRentFragment())
                .commit()
        }


        setupRecyclerView()

        freeNumberDataAdapter.onFreeNumberClickListener = {
            if (interstitialAd != null) {
                interstitialAd?.show(requireActivity())
            } else {
                Log.d(TAG, "The interstitial ad wasn't ready yet.")
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in, R.anim.fragment_out)
                .add(R.id.container, FreeRentDetailFragment.newInstance(it.id))
                .addToBackStack("details")
                .commit()
        }

        observeViewModel()


        Log.d(TAG, "onViewCreated: lifecycle")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: lifecycle")
        loadAd()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: lifecycle")
    }



    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearList()
        _binding = null
        Log.d(TAG, "task: delete fragment")

    }
    
    private fun observeViewModel() {
        viewModel.numberList.observe(viewLifecycleOwner)
        {
            freeNumberDataAdapter.submitList(it)
            if (it.isNotEmpty()) {
                binding.shimmerFreeRentLayout.visibility = View.GONE
                binding.freeRecycleView.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        freeNumberDataAdapter = FreeNumberDataAdapter()
        binding.freeRecycleView.adapter = freeNumberDataAdapter
    }

    private fun loadAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
//            "ca-app-pub-3224585614176095/1135446133",
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object: InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    Log.d(TAG, "onAdFailedToLoad: failed to load Ad")
                        interstitialAd = null
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    Log.d(TAG, "onAdLoaded: Ad loaded successfuly")
                    interstitialAd = p0
                }
            }
        )

        setInterstitialAdCallback()

    }

    private fun setInterstitialAdCallback() {
        interstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                interstitialAd = null
//                loadAd()
            }
        }
    }

    companion object {
        private const val TAG = "1"
    }

}

