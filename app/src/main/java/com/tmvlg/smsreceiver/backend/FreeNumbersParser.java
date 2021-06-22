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

    public ArrayList<FreeMessage> messagesList;





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



    public void parse_messages_1(String callnumber) {
        try {
            Document doc = Jsoup.connect("https://sms-online.co/receive-free-sms/" + callnumber).get();
            Elements titles = doc.select("h3.list-item-title");
            Elements messages = doc.select("div.list-item-content");
            Elements times = doc.select("span.list-item-meta > span");

            for (int i = 0; i < titles.size(); i++) {
                FreeMessage free_message = new FreeMessage();

                free_message.message_title = titles.get(i).text();
                free_message.message_text = messages.get(i).text();
                free_message.time_remained = times.get(i).text();

                messagesList.add(free_message);
            }


        } catch (IOException e) {
            Log.d(TAG, "parsing error at parse_messages_1");
            e.printStackTrace();
        }
    }


    /////////////////////////////////////////////////////////////
    ////////////////////https:///smska.us////////////////////////
    /////////////////////////////////////////////////////////////


    public void parse_2() {
        try {
            Document doc = Jsoup.connect("https://smska.us/").get();
            Elements numbers = doc.select("div.phone_number");

            for (int i = 0; i < numbers.size(); i++) {

                FreeNumber free_number = new FreeNumber();

                String prefix = "+7";
                String code = "RU";

                free_number.call_number = numbers.get(i).text().replace(prefix, "").replaceAll("^\\s+", "");
                free_number.call_number_prefix = prefix;
                free_number.country_code = code;
                free_number.counrty_name = "Russia";
                free_number.origin = "parse_2";

                numbersList.add(free_number);
            }
        } catch (IOException e) {
            Log.d(TAG, "parsing error at parse_2");
            e.printStackTrace();
        }

    }


    public void parse_messages_2(String callnumber) {
        try {
            Document doc = Jsoup.connect("https://smska.us/").get();
            Elements titles = doc.select("div#" + callnumber + " div:nth-child(1)");
            Elements messages = doc.select("div#" + callnumber + " > div.textsms");
            Elements times = doc.select("div#" + callnumber + " > div.smsdate");

            Log.d(TAG, "div#" + callnumber + " > div.bodysms");
            Log.d(TAG, titles.html());


            for (int i = 0; i < titles.size(); i++) {
                Log.d(TAG, "iteration++");
                Log.d(TAG, titles.get(i).text());

                FreeMessage free_message = new FreeMessage();

                free_message.message_title = titles.get(i).text();
                free_message.message_text = messages.get(i).text();
                free_message.time_remained = times.get(i).text();

                messagesList.add(free_message);
            }


        } catch (IOException e) {
            Log.d(TAG, "parsing error at parse_messages_2");
            e.printStackTrace();
        }
    }




    /////////////////////////////////////////////////////////////
    ////////////////////https://online-sms.org/////////////////
    /////////////////////////////////////////////////////////////


    public void parse_3() {
        try {
            Document doc = Jsoup.connect("https://online-sms.org/").get();
            Elements countries = doc.select(("h4.titlecoutry"));



            CountryCodes codes = new CountryCodes();
            for (int i = 0; i < countries.size(); i++) {
                Log.d(TAG, countries.get(i).text());
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

                Elements numbers = doc.select("#tab" + code + " a");
                Log.d(TAG, "#tab" + code + " a");

                for (int j = 0; j < numbers.size(); j++) {
                    Log.d(TAG, numbers.get(j).text());

                    FreeNumber free_number = new FreeNumber();

                    free_number.call_number = numbers.get(j).text().replace(prefix, "").replaceAll("^\\s+", "");
                    free_number.call_number_prefix = prefix;
                    free_number.country_code = code;
                    free_number.counrty_name = countries.get(i).text();
                    free_number.origin = "parse_3";

                    numbersList.add(free_number);
                }

            }
        } catch (IOException e) {
            Log.d(TAG, "parsing error at parse_1");
            e.printStackTrace();
        }

    }


    public void parse_messages_3(String callnumber) {
        try {
            Document doc = Jsoup.connect("https://online-sms.org/free-phone-number-" + callnumber).get();
            Elements titles = doc.select("table > tbody > tr > td:nth-child(1)");
            Elements messages = doc.select("table > tbody > tr > td:nth-child(2)");
            Elements times = doc.select("table > tbody > tr > td:nth-child(3)");

            Log.d(TAG, "div#" + callnumber + " > div.bodysms");
            Log.d(TAG, titles.html());

            for (int i = 0; i < titles.size(); i++) {
                Log.d(TAG, "iteration++");
                Log.d(TAG, titles.get(i).text());

                FreeMessage free_message = new FreeMessage();

                free_message.message_title = titles.get(i).text();
                free_message.message_text = messages.get(i).text();
                free_message.time_remained = times.get(i).text();

                messagesList.add(free_message);
            }


        } catch (IOException e) {
            Log.d(TAG, "parsing error at parse_messages_2");
            e.printStackTrace();
        }
    }




    public void parse_numbers() {
        parse_1();
//        parse_2();
        parse_3();

        sort_numbers();
    }

    public void parse_messages(String origin, String callnumber) {
        messagesList.clear();
        if (origin.equals("parse_1")) {
            Log.d(TAG, callnumber);
            parse_messages_1(callnumber);
        }
        else if (origin.equals("parse_2")) {
            Log.d(TAG, callnumber.substring(1));
            parse_messages_2(callnumber.substring(1));
        }
        else if (origin.equals("parse_3")) {
            Log.d(TAG, callnumber.substring(1));
            parse_messages_3(callnumber);
        }
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
        messagesList = new ArrayList<>();
    }



}
