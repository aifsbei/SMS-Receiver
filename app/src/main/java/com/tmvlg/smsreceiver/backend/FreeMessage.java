package com.tmvlg.smsreceiver.backend;

public class FreeMessage {
    public String message_title;
    public String message_text;
    public String time_remained;

    public FreeMessage(String message_title, String message_text, String time_remained) {
        this.message_title = message_title;
        this.message_text = message_text;
        this.time_remained = time_remained;
    }

    public FreeMessage() {

    }
}

