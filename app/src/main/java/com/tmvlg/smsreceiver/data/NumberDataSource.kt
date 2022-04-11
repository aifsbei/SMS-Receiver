package com.tmvlg.smsreceiver.data

import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.OnlineSimApi
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Service
import com.tmvlg.smsreceiver.util.addUniqueOrUpdateCurrent
import com.tmvlg.smsreceiver.util.formatCountryName
import com.tmvlg.smsreceiver.util.getCountryCodeByPrefix
import kotlinx.coroutines.flow.flow

class NumberDataSource(
    private val onlineSimApi: OnlineSimApi
) {

    fun fetchAvailableCountries(serviceId: Int) = flow {
        val countryList = mutableListOf<Country>()
        var id = 0
        onlineSimApi
            .retrofitService
            .getNumberStats()
            .forEach { countryItem ->

                val countryName = formatCountryName(countryItem.value.name)

                val country = Country(
                    id = id++,
                    name = countryName,
                    code = countryItem.value.code,
                    new = countryItem.value.new,
                    enabled = countryItem.value.enabled
                )
                var numbersCount = 0
                var price: Float = 0f
                countryItem.value.services.forEach { serviceItem ->
                    numbersCount = if (serviceItem.value.count > numbersCount)
                        serviceItem.value.count
                    else
                        numbersCount
                    if (serviceId == serviceItem.value.id) {
                        price = serviceItem.value.price
                    }
                }
                country.totalNumbers = numbersCount
                country.price = price
//            val countryCode = getCountryCodeByName(countryName) ?: ""     //WORKS BAD
                var countryCode = getCountryCodeByPrefix(countryItem.key.toInt()) ?: return@forEach
                if (countryCode == UNDEFINED_COUNTRY_CODE) {
                    countryCode = "undefined"
                }
                country.iconPath = "flag_${countryCode.lowercase()}"
                countryList.add(country)
            }
        emit(countryList)
    }

    fun fetchServiceList() = flow {
        val serviceSet = mutableSetOf<Service>()
        var id = 0

        onlineSimApi
            .retrofitService
            .getNumberStats()
            .forEach { countryItem ->
                countryItem.value.services.forEach { (_, serviceModel) ->
                    val service = Service(
                        id = id++,
                        numbersCount = serviceModel.count,
                        popular = serviceModel.popular,
                        code = serviceModel.code,
                        minPrice = serviceModel.price,
                        serviceName = serviceModel.service,
                        slug = serviceModel.slug
                    )
                    serviceSet.addUniqueOrUpdateCurrent(service)
                }
            }
        emit(serviceSet.toMutableList())
    }

    companion object {
        const val TAG = "1"
        const val UNDEFINED_COUNTRY_CODE = "ZZ"
    }
}