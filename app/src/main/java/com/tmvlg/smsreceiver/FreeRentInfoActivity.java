package com.tmvlg.smsreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_rent_info);



        arrow_back = findViewById(R.id.arrow_back);
        free_country_flag = findViewById(R.id.free_country_flag);
        free_country_flag_overlay = findViewById(R.id.free_country_flag_overlay);
        free_region_code_info = findViewById(R.id.free_region_code_info);
        free_country_name_info = findViewById(R.id.free_country_name_info);
        free_call_number_info = findViewById(R.id.free_call_number_info);
        refresh_button = findViewById(R.id.refresh_button);
        substrate_countryname = findViewById(R.id.substrate_countryname);
        substrate_callnumber = findViewById(R.id.substrate_callnumber);



        init_data();

        arrow_back.setOnClickListener(refresh_button_click_listener);

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

        /*FLAG OVERLAY*/
//        free_country_flag.setImageAlpha(255);
        Bitmap srcBitmap = getBitmap(flag_resID);
        Bitmap dstBitmap = Bitmap.createScaledBitmap(srcBitmap, 1, 1, true);
        int pixel = dstBitmap.getPixel(0,0);
//        free_country_flag.setImageAlpha(51);    //20% alpha
        Log.d(TAG, "BITMAP COLOR: R = " + Color.red(pixel) + " G = " + Color.green(pixel) + " B = " + Color.blue(pixel));
        free_country_flag_overlay.setBackgroundColor(Color.rgb(Color.red(pixel), Color.green(pixel), Color.blue(pixel)));

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



    }

    View.OnClickListener refresh_button_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(FreeRentInfoActivity.this, MainActivity.class);
//            startActivity(intent);
            FreeRentInfoActivity.super.finish();
        }
    };

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