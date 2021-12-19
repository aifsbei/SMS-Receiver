package com.tmvlg.smsreceiver.presentation.rent.newnumber

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentNewNumberBinding
import com.tmvlg.smsreceiver.presentation.rent.RentNumberFragment
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.adapter.NewNumberPagerAdapter
import kotlin.math.abs

class NewNumberFragment : Fragment() {

    private lateinit var viewModel: NewNumberViewModel

    private lateinit var pagerAdapter: NewNumberPagerAdapter

    private var _binding: FragmentNewNumberBinding? = null
    private val binding: FragmentNewNumberBinding
        get() = _binding ?: throw IllegalStateException("null binding at $this")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[NewNumberViewModel::class.java]

        _binding = FragmentNewNumberBinding.inflate(inflater, container, false)

        return binding.root
    }

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
                binding.stepper.tvStep1.visibility = View.VISIBLE
                binding.stepper.ivStep1.visibility = View.INVISIBLE
                binding.stepper.tvStep2.visibility = View.INVISIBLE
                binding.stepper.ivStep2.visibility = View.VISIBLE
                binding.stepper.tvStep3.visibility = View.INVISIBLE
                binding.stepper.ivStep3.visibility = View.VISIBLE

                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_unfilled)

                binding.nextButton.text = "Next"
                binding.nextButton.isEnabled = true

            }
            1 -> {
                binding.stepper.tvStep1.visibility = View.INVISIBLE
                binding.stepper.ivStep1.visibility = View.VISIBLE
                binding.stepper.tvStep2.visibility = View.VISIBLE
                binding.stepper.ivStep2.visibility = View.INVISIBLE
                binding.stepper.tvStep3.visibility = View.INVISIBLE
                binding.stepper.ivStep3.visibility = View.VISIBLE

                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_unfilled)

                binding.nextButton.text = "Next"
                binding.nextButton.isEnabled = true

            }
            2 -> {
                binding.stepper.tvStep1.visibility = View.INVISIBLE
                binding.stepper.ivStep1.visibility = View.VISIBLE
                binding.stepper.tvStep2.visibility = View.INVISIBLE
                binding.stepper.ivStep2.visibility = View.VISIBLE
                binding.stepper.tvStep3.visibility = View.VISIBLE
                binding.stepper.ivStep3.visibility = View.INVISIBLE

                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_unfilled)

                binding.nextButton.text = "Finish"
                binding.nextButton.isEnabled = false

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "1"
        fun newInstance() = NewNumberFragment()
    }

}