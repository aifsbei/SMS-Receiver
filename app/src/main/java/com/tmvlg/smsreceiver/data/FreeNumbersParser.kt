package com.tmvlg.smsreceiver.data

import android.util.Log
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.OnlineSimFreeApi
import com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models.FreeCountriesResponse
import com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models.MessagesResponse
import com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.models.NumbersResponse
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessage
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.util.*
import org.json.simple.parser.ParseException
import org.jsoup.Jsoup
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL
import java.util.*

class FreeNumbersParser {
    val numbersList = mutableListOf<FreeNumber>()
    val messagesList = mutableListOf<FreeMessage>()


    /////////////////////////////////////////////////////////////
    //////////https://sms-online.co/receive-free-sms/////////////
    /////////////////////////////////////////////////////////////

    fun parse_1() {
        try {
            val doc = Jsoup.connect("https://sms-online.co/receive-free-sms").get()
            val numbers = doc.select("h4.number-boxes-item-number")
            val countries = doc.select("h5.number-boxes-item-country")
            Log.d(TAG, numbers.text())
            for (i in numbers.indices) {
                val code = getCountryCodeByName(countries[i].text()) ?: continue
                val prefix = getPrefixByCountryCode(code) ?: continue
                val free_number = FreeNumber(
                    call_number = numbers[i].text().replace(prefix, "")
                        .replace("^\\s+".toRegex(), ""),
                    call_number_prefix = prefix,
                    country_code = code,
                    counrty_name = countries[i].text(),
                    origin = "parse_1",
                    icon_path = "$code.xml"
                )
                numbersList.add(free_number)
            }
        } catch (e: IOException) {
            Log.d(TAG, "parsing error at parse_1")
            e.printStackTrace()
        }
    }

    fun parse_messages_1(callnumber: String) {
        try {
            val doc = Jsoup.connect("https://sms-online.co/receive-free-sms/$callnumber").get()
            val titles = doc.select("h3.list-item-title")
            val messages = doc.select("div.list-item-content")
            val times = doc.select("span.list-item-meta > span")
            for (i in titles.indices) {
                val free_message = FreeMessage(
                    message_title = titles[i].text(),
                    message_text = messages[i].text(),
                    time_remained = times[i].text()
                )
                messagesList.add(free_message)
            }
        } catch (e: IOException) {
            Log.d(TAG, "parsing error at parse_messages_1")
            e.printStackTrace()
        }
    }

    /////////////////////////////////////////////////////////////
    ////////////////////https:///smska.us////////////////////////
    /////////////////////////////////////////////////////////////
    fun parse_2() {
        try {
            val doc = Jsoup.connect("https://smska.us/").get()
            val numbers = doc.select("div.phone_number")
            for (i in numbers.indices) {
                val prefix = "+7"
                val code = "RU"
                val free_number = FreeNumber(
                    call_number = numbers[i].text().replace(prefix, "")
                        .replace("^\\s+".toRegex(), ""),
                    call_number_prefix = prefix,
                    country_code = code,
                    counrty_name = "Russia",
                    origin = "parse_2",
                    icon_path = "$code.xml"
                )
                numbersList.add(free_number)
            }
        } catch (e: IOException) {
            Log.d(TAG, "parsing error at parse_2")
            e.printStackTrace()
        }
    }

    fun parse_messages_2(callnumber: String) {
        try {
            val doc = Jsoup.connect("https://smska.us/").get()
            val titles = doc.select("div#$callnumber div:nth-child(1)")
            val messages = doc.select("div#$callnumber > div.textsms")
            val times = doc.select("div#$callnumber > div.smsdate")
            Log.d(TAG, "div#$callnumber > div.bodysms")
            Log.d(TAG, titles.html())
            for (i in titles.indices) {
                Log.d(TAG, "iteration++")
                Log.d(TAG, titles[i].text())
                val free_message = FreeMessage(
                    message_title = titles[i].text(),
                    message_text = messages[i].text(),
                    time_remained = times[i].text()
                )
                messagesList.add(free_message)
            }
        } catch (e: IOException) {
            Log.d(TAG, "parsing error at parse_messages_2")
            e.printStackTrace()
        }
    }

    /////////////////////////////////////////////////////////////
    ////////////////////https://online-sms.org/////////////////
    /////////////////////////////////////////////////////////////
    fun parse_3() {
        try {
            val doc = Jsoup.connect("https://online-sms.org/").get()
            val countries = doc.select("h4.titlecoutry")
            for (i in countries.indices) {
                val countryName = countries[i].text()
                Log.d(TAG, countryName)
                val code = getCountryCodeByName(countryName) ?: continue
                val prefix = getPrefixByCountryCode(code)
                if (prefix == null || prefix.contains("and")) {
                    Log.d(TAG, "null number prefix at $code")
                    continue
                }
                val numbers = doc.select("#tab$code a")
                Log.d(TAG, "#tab$code a")
                for (j in numbers.indices) {
                    val number = numbers[j].text()
                    Log.d(TAG, number)
                    val numberFormatted = numbers[j].text().replace(prefix, "")
                        .replace("^\\s+".toRegex(), "")
                    val free_number = FreeNumber(
                        call_number = numberFormatted,
                        call_number_prefix = prefix,
                        country_code = code,
                        counrty_name = countryName,
                        origin = "parse_3",
                        icon_path = "$code.xml"
                    )
                    numbersList.add(free_number)
                }
            }
        } catch (e: IOException) {
            Log.d(TAG, "parsing error at parse_3")
            e.printStackTrace()
        }
    }

    fun parse_messages_3(callnumber: String) {
        try {
            val doc = Jsoup.connect("https://online-sms.org/free-phone-number-$callnumber").get()
            val titles = doc.select("table > tbody > tr > td:nth-child(1)")
            val messages = doc.select("table > tbody > tr > td:nth-child(2)")
            val times = doc.select("table > tbody > tr > td:nth-child(3)")
            Log.d(TAG, "div#$callnumber > div.bodysms")
            Log.d(TAG, titles.html())
            for (i in titles.indices) {
                Log.d(TAG, "iteration++")
                Log.d(TAG, titles[i].text())
                val free_message = FreeMessage(
                    message_title = titles[i].text(),
                    message_text = messages[i].text(),
                    time_remained = times[i].text()
                )
                messagesList.add(free_message)
            }
        } catch (e: IOException) {
            Log.d(TAG, "parsing error at parse_messages_3")
            e.printStackTrace()
        }
    }

    @Throws(ParseException::class, MalformedURLException::class)
    fun parse_4() {
        try {
            val freeCountriesResponse: Response<FreeCountriesResponse> = OnlineSimFreeApi
                .retrofitService
                .getCountryNames()
                .execute() ?: return
            val countriesBody = freeCountriesResponse.body() ?: return
            countriesBody.countries.forEach {

                val numbersResponse: Response<NumbersResponse> = OnlineSimFreeApi
                    .retrofitService
                    .getFreeNumbers(it.country)
                    .execute()

                val numbersBody: NumbersResponse = numbersResponse.body() ?: return
                numbersBody.numbers.forEach {

                    val countryCode: String = getCountryCodeByNumber(it.fullNumber)
                    val countryName = getCountryNameByCode(countryCode) ?: return

                    val freeNumber = FreeNumber(
                        call_number = it.number,
                        call_number_prefix = "+${it.country}",
                        country_code = countryCode,
                        counrty_name = countryName,
                        origin = "parse_4",
                        icon_path = "${countryCode.uppercase()}.xml"
                    )
                    numbersList.add(freeNumber)
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "parse_4: timeout")
        }
    }

    @Throws(ParseException::class, MalformedURLException::class)
    fun parse_messages_4(phone: String, prefix: String) {
        try {
            val messagesResponse: Response<MessagesResponse> = OnlineSimFreeApi
                .retrofitService
                .getFreeMessages(phone, prefix)
                .execute() ?: return
            val messagesBody = messagesResponse.body() ?: return
            messagesBody.messages.data.forEach {
                val free_message = FreeMessage(
                    message_title = it.inNumber,
                    message_text = it.text,
                    time_remained = it.dataHumans
                )
                messagesList.add(free_message)

            }
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "parse_messages_4: timeout")
        }
    }

    fun parse_5() {
        try {
            val doc = Jsoup.connect("https://receive-smss.com/").get()
            val numbers = doc.select("h4.number-boxes-itemm-number")
            val countries = doc.select("h5.number-boxes-item-country")
            val numberType = doc.select("a.number-boxes-item-button")

            for (i in numbers.indices) {
                if (numberType[i].text() == "premium") {
                    continue
                } else {
                    val code = getCountryCodeByName(countries[i].text()) ?: continue
                    val prefix = getPrefixByCountryCode(code) ?: continue
                    val free_number = FreeNumber(
                        call_number = numbers[i].text().replace(prefix, "")
                            .replace("^\\s+".toRegex(), ""),
                        call_number_prefix = prefix,
                        country_code = code,
                        counrty_name = countries[i].text(),
                        origin = "parse_5",
                        icon_path = "$code.xml"
                    )
                    numbersList.add(free_number)
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun parse_messages_5(number: String) {
        try {
            val doc = Jsoup.connect("https://receive-smss.com/sms/$number/").get()

            val sender = doc.select("tr > td.wr3pc52sel1757:nth-child(1)")
//            val messageCode = doc.select("span.btncp1 > b")
            val messageBody = doc.select("tr > td.wr3pc52sel1757:nth-child(2)")
            val time = doc.select("tr > td.wr3pc52sel1757:nth-child(3) > span")

            for (i in sender.indices) {
                val messageTitleFormatted = sender[i].text().replace("a\"", "")
                val timeRemainedFormatted = "${time[i].text()} ago"
                val free_message = FreeMessage(
                    message_title = messageTitleFormatted,
                    message_text = messageBody[i].text(),
                    time_remained = timeRemainedFormatted
                )
                messagesList.add(free_message)
            }
            println(messagesList)

        } catch (e: IOException) {
            println("parsing error")
            e.printStackTrace()
        }
    }

    @Throws(ParseException::class, MalformedURLException::class)
    fun parse_numbers() {
        parse_1()
//        parse_2()
        parse_3()
        parse_4()
        parse_5()
        add_countrynames_to_list()
        sort_numbers()
        for (number in numbersList) {
            Log.d(TAG, "parse_numbers: $number")
        }
    }

    @Throws(MalformedURLException::class, ParseException::class)
    fun parse_messages(freeNumber: FreeNumber) {
        Log.d(TAG, "parse_messages: STARTED")
        messagesList.clear()
        with(freeNumber) {
            var formated_call_number = call_number
            formated_call_number = call_number_prefix
                .substring(1) + formated_call_number
                .replace(" ", "")
                .replace("-", "")
            when (origin) {
                "parse_1" -> {
                    Log.d(TAG, formated_call_number)
                    parse_messages_1(formated_call_number)
                }
                "parse_2" -> {
                    Log.d(TAG, formated_call_number.substring(1))
                    parse_messages_2(formated_call_number.substring(1))
                }
                "parse_3" -> {
//                    Log.d(TAG, call_number.substring(1))
                    Log.d(TAG, "parse_messages: $formated_call_number")
                    parse_messages_3(formated_call_number)
                }
                "parse_4" -> {
                    Log.d(TAG, call_number)
                    parse_messages_4(call_number, call_number_prefix)
                }
                "parse_5" -> {
                    Log.d(TAG, call_number)
                    parse_messages_5(formated_call_number)
                }
                else -> throw java.lang.RuntimeException("Unknown parse origin")
            }
        }
    }

    fun sort_numbers() {
        numbersList.sortWith { o1, o2 ->
            val comparision = o1.counrty_name.compareTo(o2.counrty_name)
            comparision.compareTo(0)
        }
    }

    fun add_countrynames_to_list() {
        if (numbersList.isEmpty())
            return
        var countryName = ""
        val tempNumberList = mutableListOf<FreeNumber>()
        for (item in numbersList) {
            val new_countryName = item.counrty_name
            val new_call_number_prefix = item.call_number_prefix
            val new_country_code = item.country_code
            if (new_countryName != countryName) {
                val free_number = FreeNumber(
                    call_number = "",
                    call_number_prefix = new_call_number_prefix,
                    country_code = new_country_code,
                    counrty_name = new_countryName,
                    origin = "",
                    icon_path = "",
                    type = FreeNumber.HEADER_TYPE
                )
                if (!tempNumberList.containsCountryName(free_number.counrty_name)) {
                    tempNumberList.add(free_number)
                    countryName = new_countryName
                }
            }
        }
        for (item in tempNumberList) {
            numbersList.add(0, item)
        }
    }

    fun print_data() {
        for (cur_number in numbersList) {
            Log.d(
                TAG,
                cur_number.call_number_prefix + " : " + cur_number.call_number + " : " + cur_number.country_code
            )
        }
    }


    companion object {
        private const val TAG = "1"

        fun parse_inline(url: URL): String {
            // URL url = new URL("https://google.com");
            try {
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connect()
                val responsecode = conn.responseCode
                var inline = ""
                return if (responsecode != 200) throw RuntimeException("HttpResponseCode: $responsecode") else {
                    val sc = Scanner(url.openStream())
                    while (sc.hasNext()) {
                        inline += sc.nextLine()
                    }
                    println("INLINE FETCHED!")
                    sc.close()
                    inline
                }
            } catch (e: Exception) {
                println(e.message)
            }
            return ""
        }
    }
}