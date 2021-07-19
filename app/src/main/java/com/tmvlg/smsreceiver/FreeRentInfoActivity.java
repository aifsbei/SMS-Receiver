package com.tmvlg.smsreceiver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.neovisionaries.i18n.CountryCode;
import com.tmvlg.smsreceiver.backend.CountryCodes;
import com.tmvlg.smsreceiver.backend.FreeMessage;
import com.tmvlg.smsreceiver.backend.FreeMessagesDataAdapter;
import com.tmvlg.smsreceiver.backend.FreeNumber;
import com.tmvlg.smsreceiver.backend.FreeNumberDataAdapter;
import com.tmvlg.smsreceiver.backend.FreeNumbersParser;
import com.tmvlg.smsreceiver.backend.RecyclerItemDecoration;
import com.tmvlg.smsreceiver.ui.FreeRentFragment;

import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
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
    ImageView free_copy_icon_info;
    ImageView free_country_flag_shadow;
    int baseColor;
    int textColor;

    FreeNumbersParser parser;

    String origin;
    String call_number;


    ArrayList<HashMap<String,String>> dataList;
    RecyclerView free_messages_recycle_view;
    ShimmerFrameLayout shimmer_free_rent_info_layout;
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
        free_copy_icon_info = findViewById(R.id.free_copy_icon_info);



        refresh_button.setOnClickListener(refresh_button_click_listener);
        arrow_back.setOnClickListener(go_back_button_click_listener);
        free_copy_icon_info.setOnClickListener(free_copy_icon_info_click_listener);



        init_data();



        dataList = new ArrayList<>();

        free_messages_recycle_view = findViewById(R.id.free_messages_recycle_view);
        shimmer_free_rent_info_layout = findViewById(R.id.shimmer_free_rent_info_layout);
        linearLayoutManager = new LinearLayoutManager(this);
        free_messages_recycle_view.setLayoutManager(linearLayoutManager);

//        getData();

//        FreeMessagesDataAdapter adapter = new FreeMessagesDataAdapter(this, dataList);
//        free_messages_recycle_view.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.free_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        parser = new FreeNumbersParser();
        new FreeRentInfoActivity.AsyncParse().execute();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    public void init_data() {

        Bundle bundle = getIntent().getExtras();
        String free_region_icon = bundle.getString("free_region_icon");
        String free_prefix = bundle.getString("free_prefix");
        String free_call_number = bundle.getString("free_call_number");
        origin = bundle.getString("origin");

        String flag_path = free_region_icon.replace("flag_", "");
        String country_code = free_prefix.substring(0, 2);
        call_number = free_prefix.substring(3) + " " + free_call_number;

        Log.d(TAG, free_region_icon);
        Log.d(TAG, free_prefix);
        Log.d(TAG, free_call_number);

        Log.d(TAG, flag_path);
        Log.d(TAG, country_code);
        Log.d(TAG, call_number);


        int flag_resID = getResId(flag_path, R.xml.class);
        Bitmap srcBitmap = getBitmap(flag_resID);

        baseColor = ContextCompat.getColor(this, R.color.design_default_color_background);
        textColor = ContextCompat.getColor(this, R.color.design_default_color_secondary_variant);

//        Palette.from(srcBitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
////                int mutedColor = palette.getMutedColor(R.attr.colorPrimary);
////                collapsingToolbar.setContentScrimColor(mutedColor);
//                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
//                baseColor = vibrantSwatch.getRgb();
//                textColor = vibrantSwatch.getTitleTextColor();
//                Log.d(TAG, "baseColor = " + baseColor);
//                Log.d(TAG, "textColor = " + textColor);
//            }
//        });


        Palette palette = Palette.from(srcBitmap).generate();
        Palette.Swatch vibrantSwatch = palette.getMutedSwatch();
        if (vibrantSwatch == null) {
            vibrantSwatch = palette.getVibrantSwatch();
        }
        baseColor = vibrantSwatch.getRgb();
        textColor = vibrantSwatch.getTitleTextColor();
        Log.d(TAG, "baseColor = " + baseColor);
        Log.d(TAG, "textColor = " + textColor);




        /*SETTING FLAG*/
////        int flag_resID = getResId(flag_path, R.xml.class);
        free_country_flag.setImageResource(flag_resID);

//        Animation fadeOut = new AlphaAnimation(1f, 0.5f);
//        fadeOut.setInterpolator(new DecelerateInterpolator()); //add this
//        fadeOut.setDuration(1500);
//        fadeOut.setFillAfter(true);
//        free_country_flag.setAnimation(fadeOut);
//        fadeOut.start();

        /*FLAG OVERLAY*/
////        Bitmap srcBitmap = getBitmap(flag_resID);
////        Bitmap dstBitmap = Bitmap.createScaledBitmap(srcBitmap, 1, 1, true);
////        int pixel = dstBitmap.getPixel(0,0);
////        baseColor = Color.rgb(Color.red(pixel), Color.green(pixel), Color.blue(pixel));
//        free_country_flag.setImageAlpha(51);    //20% alpha
////        Log.d(TAG, "BITMAP COLOR: R = " + Color.red(pixel) + " G = " + Color.green(pixel) + " B = " + Color.blue(pixel));
        free_country_flag_overlay.setBackgroundColor(baseColor);

//        Animation fadeIn = new AlphaAnimation(0f, 0.2f);
//        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
//        fadeIn.setDuration(1500);
//        fadeIn.setFillAfter(true);
//        free_country_flag_overlay.setAnimation(fadeIn);
//        fadeIn.start();

        /*SHADOW OVERLAY*/
//        Animation fadeIn2 = new AlphaAnimation(0f, 0.75f);
//        fadeIn2.setInterpolator(new AccelerateInterpolator()); //add this
//        fadeIn2.setDuration(1000);
//        fadeIn2.setFillAfter(true);
//        free_country_flag_shadow.setAnimation(fadeIn2);
//        fadeIn2.start();

        /*SUBSTRATE COUNTRYNAME*/
        substrate_countryname.setBackgroundColor(baseColor);
        /*SUBSTRATE CALLNUMBER*/
        substrate_callnumber.setBackgroundColor(baseColor);
        /*REFRESH BUTTON*/
        refresh_button.setBackgroundColor(baseColor);
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

    View.OnClickListener go_back_button_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(FreeRentInfoActivity.this, MainActivity.class);
//            startActivity(intent);
            FreeRentInfoActivity.super.finish();
        }
    };

    View.OnClickListener refresh_button_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new FreeRentInfoActivity.AsyncParse().execute();
        }
    };

    View.OnClickListener free_copy_icon_info_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String to_delete = call_number.split(" ")[0];
            call_number = call_number.replace(to_delete, "");
            call_number = call_number.replaceAll("-", "");
            call_number = call_number.replaceAll(" ", "");
            ClipData clip = ClipData.newPlainText("Clipboard", call_number);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Номер скопирован!", Toast.LENGTH_SHORT).show();
        }
    };



    public void getData() {
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> dataMApi = new HashMap<>();
            dataMApi.put("Title", "Zimbabwe");
            dataMApi.put("free_message_header", "AUTORUS");
            dataMApi.put("free_message_text", "Спасибо за регистрацию в AUTORUS! Ваш проверочный код: 125854. Никому не сообщайте этот код!");
            dataMApi.put("free_time_remained", "5 минут назад");
            dataMApi.put("color", String.valueOf(baseColor));
            dataList.add(dataMApi);
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    Log.i(TAG, "You have forgotten to specify the parentActivityName in the AndroidManifest!");
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    class AsyncParse extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<ArrayList<String>> numbers_data_list;
            String cn = call_number.replace("-", "").replace(" ", "").replace("+", "");
            try {
                parser.parse_messages(origin, cn);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            shimmer_free_rent_info_layout.setVisibility(View.VISIBLE);
            free_messages_recycle_view.setVisibility(View.GONE);
            Log.d(TAG, "ON PRE EXECUTE");
            parser.messagesList.clear();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            dataList.clear();
            shimmer_free_rent_info_layout.setVisibility(View.GONE);
            free_messages_recycle_view.setVisibility(View.VISIBLE);
            for (FreeMessage msg : parser.messagesList) {
                HashMap<String, String> dataMApi = new HashMap<>();
                dataMApi.put("Title", "NoTitle");
                dataMApi.put("free_message_header", msg.message_title);
                dataMApi.put("free_message_text", msg.message_text);
                dataMApi.put("free_time_remained", msg.time_remained);
                dataMApi.put("color", String.valueOf(baseColor));
                dataList.add(dataMApi);
            }

//            getData();

            FreeMessagesDataAdapter adapter = new FreeMessagesDataAdapter(FreeRentInfoActivity.this, dataList);
            free_messages_recycle_view.setAdapter(adapter);


            super.onPostExecute(result);

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