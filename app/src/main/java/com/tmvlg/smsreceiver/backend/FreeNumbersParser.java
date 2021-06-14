package com.tmvlg.smsreceiver.backend;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FreeNumbersParser {

    private String TAG = "1";

    public ArrayList<FreeNumber> numbersList;





    /////////////////////////////////////////////////////////////
    //////////https://sms-online.co/receive-free-sms/////////////
    /////////////////////////////////////////////////////////////


    public void parse_1() {
        try {
            Document doc = Jsoup.connect("https://sms-online.co/receive-free-sms").get();
            Elements numbers = doc.select("h4.number-boxes-item-number");
            Elements countries = doc.select("h5.number-boxes-item-country");

            CountryCodes codes = new CountryCodes();
            for (int i = 0; i < numbers.size(); i++) {
                String code = codes.getCode(countries.get(i).text());
                if (code == null) {
                    Log.d(TAG, "null country code at " + countries.get(i).text());
                    continue;
                }
                String prefix = Iso2Phone.getPhone(code);
                if (prefix == null || prefix.contains("and")) {
                    Log.d(TAG, "null number prefix at " + code);
                    continue;
                }

                FreeNumber free_number = new FreeNumber();

                free_number.call_number = numbers.get(i).text().replace(prefix, "").replaceAll("^\\s+", "");
                free_number.call_number_prefix = prefix;
                free_number.country_code = code;
                free_number.counrty_name = countries.get(i).text();
                free_number.origin = "parse_1";

                numbersList.add(free_number);
            }
        } catch (IOException e) {
            Log.d(TAG, "parsing error at parse_1");
            e.printStackTrace();
        }

    }


    public void parse_numbers() {
        parse_1();
        parse_1();

        sort_numbers();
    }


    public void sort_numbers() {
        Collections.sort(numbersList, new Comparator<FreeNumber>() {
            @Override
            public int compare(FreeNumber o1, FreeNumber o2) {
                int comparision = o1.counrty_name.compareTo(o2.counrty_name);
                return Integer.compare(comparision, 0);
            }
        });
    }

    public void print_data() {
        for (FreeNumber cur_number : numbersList) {
            Log.d(TAG, cur_number.call_number_prefix + " : " + cur_number.call_number + " : " + cur_number.country_code);
        }
    }

    public FreeNumbersParser () {
        numbersList = new ArrayList<>();
    }

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////

}
