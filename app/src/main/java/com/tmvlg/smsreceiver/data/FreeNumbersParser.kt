package com.tmvlg.smsreceiver.data

import android.util.Log
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.tmvlg.smsreceiver.backend.CountryCodes
import com.tmvlg.smsreceiver.backend.Iso2Phone
import com.tmvlg.smsreceiver.data.freemessage.FreeMessageRepositoryImpl
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessage
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.jsoup.Jsoup
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class FreeNumbersParser {
    private val TAG = "1"
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
            val codes = CountryCodes()
            for (i in numbers.indices) {
                val code = codes.getCode(countries[i].text())
                if (code == null) {
                    Log.d(TAG, "null country code at " + countries[i].text())
                    continue
                }
                val prefix = Iso2Phone.getPhone(code)
                if (prefix == null || prefix.contains("and")) {
                    Log.d(TAG, "null number prefix at $code")
                    continue
                }
                val free_number = FreeNumber(call_number = numbers[i].text().replace(prefix, "").replace("^\\s+".toRegex(), ""),
                        call_number_prefix = prefix,
                        country_code = code,
                        counrty_name = countries[i].text(),
                        origin = "parse_1",
                        icon_path = "$code.xml")
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
                val free_message = FreeMessage(message_title = titles[i].text(),
                        message_text = messages[i].text(),
                        time_remained = times[i].text())
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
                val free_number = FreeNumber(call_number = numbers[i].text().replace(prefix, "").replace("^\\s+".toRegex(), ""),
                        call_number_prefix = prefix,
                        country_code = code,
                        counrty_name = "Russia",
                        origin = "parse_2",
                        icon_path = "$code.xml")
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
                val free_message = FreeMessage(message_title = titles[i].text(),
                        message_text = messages[i].text(),
                        time_remained = times[i].text())
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
            val codes = CountryCodes()
            for (i in countries.indices) {
                Log.d(TAG, countries[i].text())
                val code = codes.getCode(countries[i].text())
                if (code == null) {
                    Log.d(TAG, "null country code at " + countries[i].text())
                    continue
                }
                val prefix = Iso2Phone.getPhone(code)
                if (prefix == null || prefix.contains("and")) {
                    Log.d(TAG, "null number prefix at $code")
                    continue
                }
                val numbers = doc.select("#tab$code a")
                Log.d(TAG, "#tab$code a")
                for (j in numbers.indices) {
                    Log.d(TAG, numbers[j].text())
                    val free_number = FreeNumber(call_number = numbers[j].text().replace(prefix, "").replace("^\\s+".toRegex(), ""),
                            call_number_prefix = prefix,
                            country_code = code,
                            counrty_name = countries[i].text(),
                            origin = "parse_3",
                            icon_path = "$code.xml")
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
                val free_message = FreeMessage(message_title = titles[i].text(),
                        message_text = messages[i].text(),
                        time_remained = times[i].text())
                messagesList.add(free_message)
            }
        } catch (e: IOException) {
            Log.d(TAG, "parsing error at parse_messages_3")
            e.printStackTrace()
        }
    }

    @Throws(ParseException::class, MalformedURLException::class)
    fun parse_4() {
        val inline_countries = parse_inline(URL("https://onlinesim.ru/api/getFreeCountryList"))
        val parse_countries = JSONParser()
        val jobj_countries = parse_countries.parse(inline_countries) as JSONObject
        val countries_Array = jobj_countries["countries"] as JSONArray
        for (i in countries_Array.indices) {
            val jsonCountry = countries_Array[i] as JSONObject
            print(" << jsonCountry >> ")
            println(jsonCountry)
            // int local_country_code = Integer.parseInt((String)jsonCountry.get("country"));
            val country_name_local = jsonCountry["country_text"] as String
            val local_country_code = (jsonCountry["country"] as Long).toInt()
            val inline_numbers = parse_inline(URL("https://onlinesim.ru/api/getFreePhoneList?country=$local_country_code"))
            val parse_numbers = JSONParser()
            val jobj_numbers = parse_numbers.parse(inline_numbers) as JSONObject
            val numbers_Array = jobj_numbers["numbers"] as JSONArray
            for (j in numbers_Array.indices) {
                val jsonNumber = numbers_Array[j] as JSONObject
                print(" >>>> jsonNumber: ")
                println(jsonNumber)
                val call_number = jsonNumber["number"] as String
                val full_number = jsonNumber["full_number"] as String
                val country_prefix = full_number.replace(call_number, "")
                val phoneUtil = PhoneNumberUtil.getInstance()
                var country_code: String? = ""
                try {
                    // phone must begin with '+'
                    val numberProto = phoneUtil.parse(full_number, "")
                    country_code = phoneUtil.getRegionCodeForCountryCode(numberProto.countryCode)
                } catch (e: Exception) {
                    System.err.println("NumberParseException was thrown: $e")
                }
                val locale = Locale("", country_code)
                val country_name = locale.displayCountry
                Log.d(TAG, "parse_4: country_name $country_name")
                val free_number = FreeNumber(call_number = call_number,
                        call_number_prefix = country_prefix,
                        country_code = country_code!!,
                        counrty_name = country_name,
                        origin = "parse_4",
                        icon_path = "$country_code.xml")
                //                temp.local_country = country_name;
//                temp.local_country_code = local_country_code;
                numbersList.add(free_number)
            }
        }
    }

    @Throws(ParseException::class, MalformedURLException::class)
    fun parse_messages_4(phone: String) {
        val inline_messages = parse_inline(URL("https://onlinesim.ru/api/getFreeMessageList?cpage=1&phone=$phone"))
        Log.d(TAG, "parse_messages_4: inline = $inline_messages")
        val parse_messages = JSONParser()
        val jobj_messages = parse_messages.parse(inline_messages) as JSONObject
        val messages_Array = jobj_messages["messages"] as JSONArray
        Log.d(TAG, "parse_messages_4: MessagesAray size: " + messages_Array.size)
        for (i in messages_Array.indices) {
            val jsonMessage = messages_Array[i] as JSONObject
            print(" << jsonMessage >> ")
            println(jsonMessage)
            //            String country_name_local = (String)jsonMessage.get("country_text");
//            int local_country_code = (int) (long) jsonMessage.get("country");
        }
    }

    @Throws(ParseException::class, MalformedURLException::class)
    fun parse_numbers() {
        parse_1()
        //        parse_2();
        parse_3()
        //        parse_4();
        add_countrynames_to_list()
        sort_numbers()
    }

    @Throws(MalformedURLException::class, ParseException::class)
    fun parse_messages(freeNumber: FreeNumber) {
        messagesList.clear()
        with(freeNumber) {
            when (origin) {
                "parse_1" -> {
                    Log.d(TAG, call_number)
                    parse_messages_1(call_number)
                }
                "parse_2" -> {
                    Log.d(TAG, call_number.substring(1))
                    parse_messages_2(call_number.substring(1))
                }
                "parse_3" -> {
                    Log.d(TAG, call_number.substring(1))
                    parse_messages_3(call_number)
                }
                "parse_4" -> {
                    Log.d(TAG, call_number)
                    parse_messages_4("+$call_number")
                }
            }
        }
    }

    fun sort_numbers() {
        Collections.sort(numbersList) { o1, o2 ->
            val comparision = o1.counrty_name.compareTo(o2.counrty_name)
            Integer.compare(comparision, 0)
        }
    }

    fun add_countrynames_to_list() {
        var countryName = numbersList[0].counrty_name
        var tempNumberList = mutableListOf<FreeNumber>()
        for (item in numbersList) {
            val new_countryName = item.counrty_name
            val new_call_number_prefix = item.call_number_prefix
            val new_country_code = item.country_code
            if (!new_countryName.equals(countryName)) {
                val free_number = FreeNumber(call_number = "",
                        call_number_prefix = new_call_number_prefix,
                        country_code = new_country_code,
                        counrty_name = new_countryName,
                        origin = "",
                        icon_path = "",
                        type = FreeNumber.HEADER_TYPE)
                tempNumberList.add(free_number)
                countryName = new_countryName
            }
        }
        for (item in tempNumberList) {
            numbersList.add(0, item)
        }
    }

    fun print_data() {
        for (cur_number in numbersList) {
            Log.d(TAG, cur_number.call_number_prefix + " : " + cur_number.call_number + " : " + cur_number.country_code)
        }
    }

    companion object {
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