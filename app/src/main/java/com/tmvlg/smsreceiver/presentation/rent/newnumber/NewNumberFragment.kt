package com.tmvlg.smsreceiver.presentation.rent.newnumber

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentNewNumberBinding
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.adapter.NewNumberPagerAdapter
import kotlin.math.abs

class NewNumberFragment : Fragment(R.layout.fragment_new_number) {

    private val viewModel by viewModels<NewNumberViewModel>()

    private lateinit var pagerAdapter: NewNumberPagerAdapter

    private val binding: FragmentNewNumberBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = NewNumberPagerAdapter(this)
        binding.pager.adapter = pagerAdapter
        binding.pager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) = toggleStepper()
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.d(TAG, "onPageScrolled: posoffset = $positionOffset")
                binding.buttonsContainer.scaleX = abs(positionOffset - 0.5f) * 2
                binding.buttonsContainer.scaleY = abs(positionOffset - 0.5f) * 2
            }
        })
        binding.nextButton.setOnClickListener {
            binding.pager.apply {
                if (currentItem != 2) {
                    currentItem += 1
                }
            }

        }
        binding.arrowBack.setOnClickListener {
            binding.pager.apply {
                if (currentItem != 0) {
                    currentItem -= 1
                }
                else {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
        binding.cancelButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun toggleStepper() {
        when (binding.pager.currentItem) {
            0 -> {
                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_unfilled)

                binding.nextButton.text = "Next"
                binding.nextButton.isEnabled = true

            }
            1 -> {
                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_unfilled)

                binding.nextButton.text = "Next"
                binding.nextButton.isEnabled = true

            }
            2 -> {
                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_success)

                binding.nextButton.text = "Finish"
                binding.nextButton.isEnabled = false

            }
        }
    }

    companion object {
        const val TAG = "1"
        fun newInstance() = NewNumberFragment()
    }

}