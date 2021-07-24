package com.tmvlg.smsreceiver.backend;

import android.os.AsyncTask;
import android.util.Log;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.neovisionaries.i18n.CountryCode;

import org.hamcrest.core.Is;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

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

            Log.d(TAG, numbers.text());

//            CountryCodes codes = new CountryCodes();
            for (int i = 0; i < numbers.size(); i++) {
//                String code = codes.getCode(countries.get(i).text());
                Log.d(TAG, "parse_1: country_name = " + countries.get(i).text());
                String code =  CountryCode.findByName(countries.get(i).text()).get(0).name();


                if (code == null) {
                    Log.d(TAG, "null country code at " + countries.get(i).text());
                    continue;
                }
//                String prefix = Iso2Phone.getPhone(code);

                int prefix2 = PhoneNumberUtil.getInstance().getCountryCodeForRegion(code);
                String prefix = "+" + prefix2;
                Log.d(TAG, "parse_1: prefix2 = " + prefix2);

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
                Log.d(TAG, "parse_3: country = " + countries.get(i).text());
                String code = codes.getCode(countries.get(i).text());
//                String code = convertCountryNameToIsoCode(countries.get(i).text()).getDisplayCountry();
                Log.d(TAG, "parse_3: code = " + code);

                if (code == null) {
                    Log.d(TAG, "null country code at " + countries.get(i).text());
                    try {
                        code = CountryCode.findByName(countries.get(i).text()).get(0).name();
                    }
                    catch (Exception e){
                        Log.d(TAG, "parse_3: null cc again");
                        continue;
                    }
                }
//                String prefix = Iso2Phone.getPhone(code);
                int prefix2 = PhoneNumberUtil.getInstance().getCountryCodeForRegion(code);
                String prefix = "+" + prefix2;
                Log.d(TAG, "parse_3: prefix = " + prefix);
                if (prefix == null || prefix.contains("and")) {
                    Log.d(TAG, "null number prefix at " + code);
                    continue;
                }

                Elements numbers = doc.select("#tab" + code + " a");
                Log.d(TAG, "parse_3: elemnumbs = " + numbers.text());
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
            Log.d(TAG, "parsing error at parse_3");
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
            Log.d(TAG, "parsing error at parse_messages_3");
            e.printStackTrace();
        }
    }

    public void parse_4() throws ParseException, MalformedURLException {
        String inline_countries = parse_inline(new URL("https://onlinesim.ru/api/getFreeCountryList"));
        JSONParser parse_countries = new JSONParser();
        JSONObject jobj_countries = (JSONObject)parse_countries.parse(inline_countries);
        JSONArray countries_Array = (JSONArray) jobj_countries.get("countries");
        for (int i = 0; i < countries_Array.size(); i++) {
            JSONObject jsonCountry = (JSONObject)countries_Array.get(i);
            System.out.print(" << jsonCountry >> ");
            System.out.println(jsonCountry);
            // int local_country_code = Integer.parseInt((String)jsonCountry.get("country"));
            String country_name_local = (String)jsonCountry.get("country_text");
            int local_country_code = (int) (long) jsonCountry.get("country");


            String inline_numbers = parse_inline(new URL("https://onlinesim.ru/api/getFreePhoneList?country=" + local_country_code));
            JSONParser parse_numbers = new JSONParser();
            JSONObject jobj_numbers = (JSONObject)parse_numbers.parse(inline_numbers);
            JSONArray numbers_Array = (JSONArray) jobj_numbers.get("numbers");
            for (int j = 0; j < numbers_Array.size(); j++) {
                JSONObject jsonNumber = (JSONObject) numbers_Array.get(j);
                System.out.print(" >>>> jsonNumber: ");
                System.out.println(jsonNumber);
                String call_number = (String) jsonNumber.get("number");
                String full_number = (String) jsonNumber.get("full_number");
                String country_prefix = full_number.replace(call_number, "");

                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

                String country_code = "";
                try {
                    // phone must begin with '+'
                    Phonenumber.PhoneNumber numberProto = phoneUtil.parse(full_number, "");
                    country_code = phoneUtil.getRegionCodeForCountryCode(numberProto.getCountryCode());
                } catch (Exception e) {
                    System.err.println("NumberParseException was thrown: " + e.toString());
                }

                Locale locale = new Locale("", country_code);
                String country_name = locale.getDisplayCountry();
                Log.d(TAG, "parse_4: country_name " + country_name);

                FreeNumber temp = new FreeNumber();
//                temp.local_country = country_name;
//                temp.local_country_code = local_country_code;
                temp.call_number = call_number;
                temp.country_code = country_code;
                temp.country_code = country_code;
                temp.counrty_name = country_name;
                temp.call_number_prefix = country_prefix;
                temp.origin = "parse_4";

                numbersList.add(temp);


            }
        }
    }




    public void parse_messages_4(String phone) throws ParseException, MalformedURLException {
        String inline_messages = parse_inline(new URL("https://onlinesim.ru/api/getFreeMessageList?cpage=1&phone=" + phone));
        Log.d(TAG, "parse_messages_4: inline = " + inline_messages);
        JSONParser parse_messages = new JSONParser();
        JSONObject jobj_messages = (JSONObject)parse_messages.parse(inline_messages);
        JSONArray messages_Array = (JSONArray) jobj_messages.get("messages");
        Log.d(TAG, "parse_messages_4: MessagesAray size: " + messages_Array.size());
        for (int i = 0; i < messages_Array.size(); i++) {
            JSONObject jsonMessage = (JSONObject)messages_Array.get(i);
            System.out.print(" << jsonMessage >> ");
            System.out.println(jsonMessage);
//            String country_name_local = (String)jsonMessage.get("country_text");
//            int local_country_code = (int) (long) jsonMessage.get("country");
        }
    }



    public void parse_numbers() throws ParseException, MalformedURLException {
//        parse_1();
//        parse_2();
        parse_3();
//        parse_4();

        sort_numbers();
    }

    public void parse_messages(String origin, String callnumber) throws MalformedURLException, ParseException {
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
        else if (origin.equals("parse_4")) {
            Log.d(TAG, callnumber);
            parse_messages_4("+" + callnumber);
        }
    }


    public static String parse_inline(URL url) {
        // URL url = new URL("https://google.com");

        try {

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            String inline = "";

            if(responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " +responsecode);
            else
            {
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext())
                {
                    inline+=sc.nextLine();
                }
                System.out.println("INLINE FETCHED!");
                sc.close();
                return inline;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
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

    Locale convertCountryNameToIsoCode(String countryName){
        for(Locale l : Locale.getAvailableLocales()) {
            if (l.getDisplayCountry().equals(countryName)) {
                return l;
            }
        }
        return null;
    }

    public FreeNumbersParser () {
        numbersList = new ArrayList<>();
        messagesList = new ArrayList<>();
    }



}
