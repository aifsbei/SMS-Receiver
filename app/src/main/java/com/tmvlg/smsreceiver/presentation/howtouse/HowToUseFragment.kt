package com.tmvlg.smsreceiver.presentation.howtouse

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager2.widget.ViewPager2
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentHowToUseBinding
import com.tmvlg.smsreceiver.databinding.FragmentNewNumberBinding
import com.tmvlg.smsreceiver.presentation.MainActivityFree
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentFragment
import com.tmvlg.smsreceiver.presentation.howtouse.pager.adapter.HowToUsePagerAdapter
import kotlin.math.abs

class HowToUseFragment : Fragment() {

    private lateinit var pagerAdapter: HowToUsePagerAdapter

    private var _binding: FragmentHowToUseBinding? = null
    private val binding: FragmentHowToUseBinding
        get() = _binding ?: throw IllegalStateException("null binding at $this")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHowToUseBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = HowToUsePagerAdapter(this)
        binding.pager.adapter = pagerAdapter
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) = toggleStepper()

//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                binding.nextButton.scaleX = abs(positionOffset - 0.5f) * 2
//                binding.nextButton.scaleY = abs(positionOffset - 0.5f) * 2
//            }
        })
        binding.nextButton.setOnClickListener {
            binding.pager.apply {
                if (currentItem != 3) {
                    currentItem += 1
                }
                else {
                    nextFragment()
                }
            }

        }
        binding.cancelButton.setOnClickListener {
            nextFragment()
        }
    }

    private fun nextFragment() {
        val intent = Intent(requireActivity(), MainActivityFree::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun toggleStepper() {

//        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.round_chevron_right_24)
//        val drawableInvisible = ContextCompat.getDrawable(requireContext(), R.drawable.chevron_right_invisible)
        val drawable = R.drawable.round_chevron_right_24
        val drawableInvisible = 0

        when (binding.pager.currentItem) {
            0 -> {
                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep4.setBackgroundResource(R.drawable.round_indicator_unfilled)

                binding.nextButton.text = getString(R.string.next_button)
                binding.nextButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)

            }
            1 -> {
                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep4.setBackgroundResource(R.drawable.round_indicator_unfilled)

                binding.nextButton.text = getString(R.string.next_button)
                binding.nextButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)

            }
            2 -> {
                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_success)
                binding.stepper.ivStep4.setBackgroundResource(R.drawable.round_indicator_unfilled)

                binding.nextButton.text = getString(R.string.next_button)
                binding.nextButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)

            }
            3 -> {
                binding.stepper.ivStep1.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep2.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep3.setBackgroundResource(R.drawable.round_indicator_unfilled)
                binding.stepper.ivStep4.setBackgroundResource(R.drawable.round_indicator_success)

                binding.nextButton.text = getString(R.string.got_it_button)
                binding.nextButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableInvisible, 0)

            }
        }

    }
}