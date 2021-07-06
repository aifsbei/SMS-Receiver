package com.tmvlg.smsreceiver.backend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tmvlg.smsreceiver.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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

        int baseColor = Integer.parseInt(Objects.requireNonNull(dataMap.get("color")));

        holder.free_message_header.setTextColor(baseColor);

//        holder.free_time_remained;
        Drawable unwrappedDrawable = holder.free_time_remained.getBackground();
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, baseColor);

        holder.free_time_remained.setTextColor(baseColor);

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
            free_message_header = (TextView)itemView.findViewById(R.id.free_message_header_shimmer);
            free_message_text = (TextView)itemView.findViewById(R.id.free_message_text);
            free_time_remained = (TextView)itemView.findViewById(R.id.free_time_remained_shimmer);
        }
    }

}