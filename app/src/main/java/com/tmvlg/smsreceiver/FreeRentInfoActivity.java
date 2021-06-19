package com.tmvlg.smsreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.neovisionaries.i18n.CountryCode;
import com.tmvlg.smsreceiver.backend.CountryCodes;
import com.tmvlg.smsreceiver.backend.FreeMessagesDataAdapter;
import com.tmvlg.smsreceiver.backend.FreeNumberDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.tmvlg.smsreceiver.backend.FreeNumberDataAdapter.getResId;

public class FreeRentInfoActivity extends AppCompatActivity {

    String TAG="freeRentInfo";

    ImageView arrow_back;
    ImageView free_country_flag;
    ImageView free_country_flag_overlay;
    TextView free_region_code_info;
    TextView free_country_name_info;
    TextView free_call_number_info;
    MaterialButton refresh_button;
    View substrate_countryname;
    View substrate_callnumber;
    ImageView free_country_flag_shadow;


    ArrayList<HashMap<String,String>> dataList;
    RecyclerView free_messages_recycle_view;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_rent_info);

        Fade fade = new Fade();
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        arrow_back = findViewById(R.id.arrow_back);
        free_country_flag = findViewById(R.id.free_country_flag);
        free_country_flag_overlay = findViewById(R.id.free_country_flag_overlay);
        free_region_code_info = findViewById(R.id.free_region_code_info);
        free_country_name_info = findViewById(R.id.free_country_name_info);
        free_call_number_info = findViewById(R.id.free_call_number_info);
        refresh_button = findViewById(R.id.refresh_button);
        substrate_countryname = findViewById(R.id.substrate_countryname);
        substrate_callnumber = findViewById(R.id.substrate_callnumber);
        free_country_flag_shadow = findViewById(R.id.free_country_flag_shadow);



        init_data();

        arrow_back.setOnClickListener(refresh_button_click_listener);

        dataList = new ArrayList<>();

        free_messages_recycle_view = findViewById(R.id.free_messages_recycle_view);
        linearLayoutManager = new LinearLayoutManager(this);
        free_messages_recycle_view.setLayoutManager(linearLayoutManager);

        getData();

        FreeMessagesDataAdapter adapter = new FreeMessagesDataAdapter(this, dataList);
        free_messages_recycle_view.setAdapter(adapter);

        getSupportActionBar().hide();


    }


    public void init_data() {

        Bundle bundle = getIntent().getExtras();
        String free_region_icon = bundle.getString("free_region_icon");
        String free_prefix = bundle.getString("free_prefix");
        String free_call_number = bundle.getString("free_call_number");

        String flag_path = free_region_icon.replace("flag_", "");
        String country_code = free_prefix.substring(0, 2);
        String call_number = free_prefix.substring(3) + " " + free_call_number;

        Log.d(TAG, free_region_icon);
        Log.d(TAG, free_prefix);
        Log.d(TAG, free_call_number);

        Log.d(TAG, flag_path);
        Log.d(TAG, country_code);
        Log.d(TAG, call_number);


        /*SETTING FLAG*/
        int flag_resID = getResId(flag_path, R.xml.class);
        free_country_flag.setImageResource(flag_resID);

        Animation fadeOut = new AlphaAnimation(1f, 0.5f);
        fadeOut.setInterpolator(new DecelerateInterpolator()); //add this
        fadeOut.setDuration(1500);
        fadeOut.setFillAfter(true);
        free_country_flag.setAnimation(fadeOut);
        fadeOut.start();

        /*FLAG OVERLAY*/
        Bitmap srcBitmap = getBitmap(flag_resID);
        Bitmap dstBitmap = Bitmap.createScaledBitmap(srcBitmap, 1, 1, true);
        int pixel = dstBitmap.getPixel(0,0);
//        free_country_flag.setImageAlpha(51);    //20% alpha
        Log.d(TAG, "BITMAP COLOR: R = " + Color.red(pixel) + " G = " + Color.green(pixel) + " B = " + Color.blue(pixel));
        free_country_flag_overlay.setBackgroundColor(Color.rgb(Color.red(pixel), Color.green(pixel), Color.blue(pixel)));

        Animation fadeIn = new AlphaAnimation(0f, 0.2f);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1500);
        fadeIn.setFillAfter(true);
        free_country_flag_overlay.setAnimation(fadeIn);
        fadeIn.start();

        /*SHADOW OVERLAY*/
//        Animation fadeIn2 = new AlphaAnimation(0f, 0.75f);
//        fadeIn2.setInterpolator(new AccelerateInterpolator()); //add this
//        fadeIn2.setDuration(1000);
//        fadeIn2.setFillAfter(true);
//        free_country_flag_shadow.setAnimation(fadeIn2);
//        fadeIn2.start();

        /*SUBSTRATE COUNTRYNAME*/
        substrate_countryname.setBackgroundColor(Color.rgb(Color.red(pixel), Color.green(pixel), Color.blue(pixel)));
        /*SUBSTRATE CALLNUMBER*/
        substrate_callnumber.setBackgroundColor(Color.rgb(Color.red(pixel), Color.green(pixel), Color.blue(pixel)));
        /*REFRESH BUTTON*/
        refresh_button.setBackgroundColor(Color.rgb(Color.red(pixel), Color.green(pixel), Color.blue(pixel)));
        refresh_button.setRippleColor(ColorStateList.valueOf(Color.rgb(228, 228, 228)));



        /*CODE*/
        free_region_code_info.setText(country_code);
        /*NUMBER*/
        free_call_number_info.setText(call_number);
        /*COUNTRY*/
        CountryCode cc = CountryCode.getByCode(country_code);
        String countryname = cc.getName();
        free_country_name_info.setText(countryname);



    }

    View.OnClickListener refresh_button_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(FreeRentInfoActivity.this, MainActivity.class);
//            startActivity(intent);
            FreeRentInfoActivity.super.finish();
        }
    };



    public void getData() {
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> dataMApi = new HashMap<>();
            dataMApi.put("Title", "Zimbabwe");
            dataMApi.put("free_message_header", "AUTORUS");
            dataMApi.put("free_message_text", "Спасибо за регистрацию в AUTORUS! Ваш проверочный код: 125854. Никому не сообщайте этот код!");
            dataMApi.put("free_time_remained", "5 минут назад");
            dataList.add(dataMApi);
        }

    }

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}