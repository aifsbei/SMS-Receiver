package com.tmvlg.smsreceiver.presentation.howtouse.pager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tmvlg.smsreceiver.presentation.howtouse.pager.additionalinfo.AdditionalInfoPagerFragment
import com.tmvlg.smsreceiver.presentation.howtouse.pager.copynumber.CopyNumberPagerFragment
import com.tmvlg.smsreceiver.presentation.howtouse.pager.refreshmessages.RefreshMessagesPagerFragment
import com.tmvlg.smsreceiver.presentation.howtouse.pager.selectnumber.SelectNumberPagerFragment
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.payfornumber.PayForNumberFragment
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.SelectCountryFragment
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.SelectServiceFragment
import java.lang.RuntimeException

class HowToUsePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> {
                SelectNumberPagerFragment()
            }
            1 -> {
                CopyNumberPagerFragment()
            }
            2 -> {
                RefreshMessagesPagerFragment()
            }
            3 -> {
                AdditionalInfoPagerFragment()
            }
            else -> {
                throw RuntimeException("No such fragment at position $position")
            }
        }
        return fragment
    }
}