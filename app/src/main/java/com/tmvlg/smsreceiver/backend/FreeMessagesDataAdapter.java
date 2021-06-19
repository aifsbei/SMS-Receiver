package com.tmvlg.smsreceiver.backend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmvlg.smsreceiver.FreeRentInfoActivity;
import com.tmvlg.smsreceiver.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class FreeMessagesDataAdapter extends RecyclerView.Adapter<FreeMessagesDataAdapter.DataHolder> {

    Context context;
    ArrayList<HashMap<String,String>> list = new ArrayList<>();
    public FreeMessagesDataAdapter(Context con, ArrayList<HashMap<String,String>> dataList)
    {
        context = con;
        list = dataList;
    }
    @NonNull
    @Override
    public FreeMessagesDataAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.free_message,parent,false);
        FreeMessagesDataAdapter.DataHolder dataHolder = new FreeMessagesDataAdapter.DataHolder(view);
        return dataHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull FreeMessagesDataAdapter.DataHolder holder, int position) {
        HashMap<String,String> dataMap = list.get(position);



        holder.free_message_header.setText(dataMap.get("free_message_header"));

        holder.free_message_text.setText(dataMap.get("free_message_text"));

        holder.free_time_remained.setText(dataMap.get("free_time_remained"));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder
    {
        TextView free_message_header;
        TextView free_message_text;
        TextView free_time_remained;
        public DataHolder(@NonNull View itemView) {
            super(itemView);
            free_message_header = (TextView)itemView.findViewById(R.id.free_message_header);
            free_message_text = (TextView)itemView.findViewById(R.id.free_message_text);
            free_time_remained = (TextView)itemView.findViewById(R.id.free_time_remained);
        }
    }

}