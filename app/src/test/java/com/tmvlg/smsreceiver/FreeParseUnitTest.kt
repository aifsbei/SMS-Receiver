package com.tmvlg.smsreceiver

import android.util.Log
import com.tmvlg.smsreceiver.data.FreeNumbersParser
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessage
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.util.getCountryCodeByName
import com.tmvlg.smsreceiver.util.getPrefixByCountryCode
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.junit.Test
import java.io.IOException

class FreeParseUnitTest {

    @Test
    fun parse5(){
        try {
            val numbersList = mutableListOf<FreeNumber>()

            val doc = Jsoup.connect("https://receive-smss.com/").get()
            val numbers = doc.select("h4.number-boxes-itemm-number")
            val countries = doc.select("h5.number-boxes-item-country")
            val numberType = doc.select("a.number-boxes-item-button")

            println("numbers")
            println(numbers.size)
            println("countries")
            println(countries.size)
            println("numberType")
            println(numberType.size)

            for (i in numbers.indices) {
                if (numberType[i].text() == "premium") {
                    continue
                }
                else {
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
            println(numbersList)

        } catch (e: IOException) {
            println("parsing error")
            e.printStackTrace()
        }
    }

    @Test
    fun parseMessages5(){
        try {
            val messagesList = mutableListOf<FreeMessage>()

            val number = "79262149162"

            val doc = Jsoup.connect("https://receive-smss.com/sms/$number/").get()


            val sender = doc.select("tr > td.wr3pc52sel1757:nth-child(1)")
//            val messageCode = doc.select("span.btncp1 > b")
            val messageBody = doc.select("tr > td.wr3pc52sel1757:nth-child(2)")
            val time = doc.select("tr > td.wr3pc52sel1757:nth-child(3) > span")

            println("sender")
            println(sender.size)
//            println("messageCode")
//            println(messageCode.text())
            println("messageBody")
            println(messageBody.size)
            println("time")
            println(time.size)

            for (i in sender.indices) {
                val messageTitleFormatted = sender[i].text().replace("a\"", "")
                val free_message = FreeMessage(
                    message_title = messageTitleFormatted,
                    message_text = messageBody[i].text(),
                    time_remained = time[i].text()
                )
                messagesList.add(free_message)
            }
            println(messagesList)

        } catch (e: IOException) {
            println("parsing error")
            e.printStackTrace()
        }
    }
}