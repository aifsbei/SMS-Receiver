package com.tmvlg.smsreceiver

import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.OnlineSimApi
import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.models.CountryModel
import com.tmvlg.smsreceiver.domain.numberforrent.searchnumber.Country
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response
import java.lang.RuntimeException

public class OnlineSimUnitTest {
    @Test
    public fun parseCountries() = runBlocking {
        val countriesResponse: Response<Map<String, CountryModel>> = OnlineSimApi
            .retrofitService
            .getCountries()
            .execute() ?: throw RuntimeException()
        val countriesBody = countriesResponse.body() ?: throw RuntimeException()
        countriesBody.entries.forEach { countryItem ->
            val country = Country(
                name = countryItem.value.name,
                code = countryItem.value.code,
                new = countryItem.value.new,
                enabled = countryItem.value.enabled
            )
            var numbersCount = 0
            countryItem.value.services.forEach { serviceItem ->
                numbersCount = if (serviceItem.value.count > numbersCount)
                    serviceItem.value.count
                else
                    numbersCount
            }
            country.totalNumbers = numbersCount
            println(country)
        }
    }

}