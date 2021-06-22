package com.tmvlg.smsreceiver.backend;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tmvlg.smsreceiver.FreeRentInfoActivity;
import com.tmvlg.smsreceiver.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FreeNumberDataAdapter extends RecyclerView.Adapter<FreeNumberDataAdapter.DataHolder> {

    Context context;
    ArrayList<HashMap<String,String>> list = new ArrayList<>();
    public FreeNumberDataAdapter(Context con, ArrayList<HashMap<String,String>> dataList)
    {
        context = con;
        list = dataList;
    }
    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.free_number,parent,false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        HashMap<String,String> dataMap = list.get(position);

//        String icon_path = dataMap.get("free_region_icon");
//        Log.d("2", icon_path);
//        int resID = context.getResources().getIdentifier(icon_path, "xml", context.getPackageResourcePath());
//        holder.free_region_icon.setImageResource(resID);


        String icon_path = dataMap.get("free_region_icon");
        Log.d("1", icon_path);
        int resID = getResId(icon_path, R.xml.class);
        holder.free_region_icon.setImageResource(resID);

//        String icon_path = dataMap.get("free_region_icon");
//        Log.d("2", icon_path);
//        InputStream is = context.getResources().openRawResource(
//                context.getResources().getIdentifier(icon_path,
//                        "xml", context.getPackageName()));
//        Bitmap bitmap = BitmapFactory.decodeStream(is);
//        holder.free_region_icon.setImageBitmap(bitmap);

        holder.free_prefix.setText(dataMap.get("free_prefix"));

        holder.free_call_number.setText(dataMap.get("free_call_number"));



        holder.free_number_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FreeRentInfoActivity.class);
                intent.putExtra("free_region_icon", dataMap.get("free_region_icon"));
                intent.putExtra("free_prefix", dataMap.get("free_prefix"));
                intent.putExtra("free_call_number", dataMap.get("free_call_number"));
                intent.putExtra("origin", dataMap.get("origin"));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.free_region_icon, "free_flag_transition");
                context.startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder
    {
        ImageView free_region_icon;
        TextView free_prefix;
        TextView free_call_number;
        LinearLayout free_number_layout;
        public DataHolder(@NonNull View itemView) {
            super(itemView);
            free_region_icon = (ImageView)itemView.findViewById(R.id.free_region_icon);
            free_prefix = (TextView)itemView.findViewById(R.id.free_prefix);
            free_call_number = (TextView)itemView.findViewById(R.id.free_call_number);
            free_number_layout = (LinearLayout)itemView.findViewById(R.id.free_number_layout);
        }
    }







    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}