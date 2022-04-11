package com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search.countrylist


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.ItemCountryBinding
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country

class SearchCountryListAdapter : ListAdapter<Country, SearchCountryViewHolder>(SearchCountryDiffCallback()) {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemCountryBinding.inflate(inflater, parent, false)
        return SearchCountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchCountryViewHolder, position: Int) {
        val country = getItem(position)

        with(holder) {
            binging.countryName.text = country.name
            binging.priceLabel.text = "${country.price} ₽"
            binging.numbersCountLabel.text = "${country.totalNumbers} numbers available"
            if (country.new)
                binging.newIndicator.visibility = View.VISIBLE
            else
                binging.newIndicator.visibility = View.INVISIBLE

            Log.d("1", "onBindViewHolder: ${country.iconPath}")
            val resID = context
                ?.getResources()
                ?.getIdentifier(
                    country.iconPath,
                    "xml",
                    context?.packageName
                )

            binging.countryIcon.setImageResource(resID ?: 0)
        }

    }
}