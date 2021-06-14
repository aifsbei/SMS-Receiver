package com.tmvlg.smsreceiver.backend;

public class FreeNumber {
    public String call_number;
    public String call_number_prefix;
    public String counrty_name;
    public String country_code;
    public String origin;

    public FreeNumber(String call_number, String counrty_name, String country_code, String origin) {
        this.call_number = call_number;
        this.counrty_name = counrty_name;
        this.country_code = country_code;
        this.origin = origin;
    }

    public FreeNumber() {

    }
}
