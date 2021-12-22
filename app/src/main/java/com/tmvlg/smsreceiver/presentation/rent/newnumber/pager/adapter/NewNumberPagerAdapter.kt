package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.payfornumber.PayForNumberFragment
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.SelectCountryFragment
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.SelectServiceFragment
import java.lang.RuntimeException

class NewNumberPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    // TODO("Отключить свайп вправо")

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> {
                SelectCountryFragment()
            }
            1 -> {
                SelectServiceFragment()
            }
            2 -> {
                PayForNumberFragment()
            }
            else -> {
                throw RuntimeException("No such fragment at position $position")
            }
        }
        return fragment
    }
}