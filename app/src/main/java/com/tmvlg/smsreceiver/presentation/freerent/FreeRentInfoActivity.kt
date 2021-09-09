package com.tmvlg.smsreceiver.presentation.freerent

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.neovisionaries.i18n.CountryCode
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.R.xml
import com.tmvlg.smsreceiver.backend.FreeMessagesDataAdapter
import com.tmvlg.smsreceiver.data.FreeNumbersParser
import com.tmvlg.smsreceiver.presentation.freerent.FreeNumberDataAdapter.Companion.getResId
import java.util.*

class FreeRentInfoActivity : AppCompatActivity() {
    var TAG = "freeRentInfo"
    var arrow_back: ImageView? = null
    var free_country_flag: ImageView? = null
    var free_country_flag_overlay: ImageView? = null
    var free_region_code_info: TextView? = null
    var free_country_name_info: TextView? = null
    var free_call_number_info: TextView? = null
    var refresh_button: MaterialButton? = null
    var substrate_countryname: View? = null
    var substrate_callnumber: View? = null
    var free_copy_icon_info: ImageView? = null
    var free_country_flag_shadow: ImageView? = null
    var baseColor = 0
    var textColor = 0
    var parser: FreeNumbersParser? = null
    var origin: String? = null
    var call_number: String? = null
    var dataList: ArrayList<HashMap<String, String>>? = null
    var free_messages_recycle_view: RecyclerView? = null
    var shimmer_free_rent_info_layout: ShimmerFrameLayout? = null
    var linearLayoutManager: LinearLayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_free_rent_info)
        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade
        arrow_back = findViewById(R.id.arrow_back)
        free_country_flag = findViewById(R.id.free_country_flag)
        free_country_flag_overlay = findViewById(R.id.free_country_flag_overlay)
        free_region_code_info = findViewById(R.id.free_region_code_info)
        free_country_name_info = findViewById(R.id.free_country_name_info)
        free_call_number_info = findViewById(R.id.free_call_number_info)
        refresh_button = findViewById(R.id.refresh_button)
        substrate_countryname = findViewById(R.id.substrate_countryname)
        substrate_callnumber = findViewById(R.id.substrate_callnumber)
        free_country_flag_shadow = findViewById(R.id.free_country_flag_shadow)
        free_copy_icon_info = findViewById(R.id.free_copy_icon_info)
//        refresh_button?.setOnClickListener(refresh_button_click_listener)
        arrow_back?.setOnClickListener(go_back_button_click_listener)
        free_copy_icon_info?.setOnClickListener(free_copy_icon_info_click_listener)
        init_data()
        dataList = ArrayList()
        free_messages_recycle_view = findViewById(R.id.free_messages_recycle_view)
        shimmer_free_rent_info_layout = findViewById(R.id.shimmer_free_rent_info_layout)
        linearLayoutManager = LinearLayoutManager(this)
        free_messages_recycle_view?.setLayoutManager(linearLayoutManager)

//        getData();

//        FreeMessagesDataAdapter adapter = new FreeMessagesDataAdapter(this, dataList);
//        free_messages_recycle_view.setAdapter(adapter);
        val toolbar = findViewById<View>(R.id.free_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        parser = FreeNumbersParser()
//        AsyncParse().execute()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    fun init_data() {
        val bundle = intent.extras
        val free_region_icon = bundle!!.getString("free_region_icon")
        val free_prefix = bundle.getString("free_prefix")
        val free_call_number = bundle.getString("free_call_number")
        origin = bundle.getString("origin")
        val flag_path = free_region_icon!!.replace("flag_", "")
        val country_code = free_prefix!!.substring(0, 2)
        call_number = free_prefix.substring(3) + " " + free_call_number
        Log.d(TAG, free_region_icon)
        Log.d(TAG, free_prefix)
        Log.d(TAG, free_call_number!!)
        Log.d(TAG, flag_path)
        Log.d(TAG, country_code)
        Log.d(TAG, call_number!!)
        val flag_resID = getResId(flag_path, xml::class.java)
        val srcBitmap = getBitmap(flag_resID)
        baseColor = ContextCompat.getColor(this, R.color.design_default_color_background)
        textColor = ContextCompat.getColor(this, R.color.design_default_color_secondary_variant)

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
        val palette = Palette.from(srcBitmap).generate()
        var vibrantSwatch = palette.mutedSwatch
        if (vibrantSwatch == null) {
            vibrantSwatch = palette.vibrantSwatch
        }
        baseColor = vibrantSwatch!!.rgb
        textColor = vibrantSwatch.titleTextColor
        Log.d(TAG, "baseColor = $baseColor")
        Log.d(TAG, "textColor = $textColor")


        /*SETTING FLAG*/
////        int flag_resID = getResId(flag_path, R.xml.class);
        free_country_flag!!.setImageResource(flag_resID)

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
        free_country_flag_overlay!!.setBackgroundColor(baseColor)

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

        /*SUBSTRATE COUNTRYNAME*/substrate_countryname!!.setBackgroundColor(baseColor)
        /*SUBSTRATE CALLNUMBER*/substrate_callnumber!!.setBackgroundColor(baseColor)
        /*REFRESH BUTTON*/refresh_button!!.setBackgroundColor(baseColor)
        refresh_button!!.rippleColor = ColorStateList.valueOf(Color.rgb(228, 228, 228))


        /*CODE*/free_region_code_info!!.text = country_code
        /*NUMBER*/free_call_number_info!!.text = call_number
        /*COUNTRY*/
        val cc = CountryCode.getByCode(country_code)
        val countryname = cc.getName()
        free_country_name_info!!.text = countryname
    }

    var go_back_button_click_listener = View.OnClickListener { //            Intent intent = new Intent(FreeRentInfoActivity.this, MainActivity.class);
//            startActivity(intent);
        super@FreeRentInfoActivity.finish()
    }
//    var refresh_button_click_listener = View.OnClickListener { AsyncParse().execute() }
    var free_copy_icon_info_click_listener = View.OnClickListener {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val to_delete = call_number!!.split(" ").toTypedArray()[0]
        call_number = call_number!!.replace(to_delete, "")
        call_number = call_number!!.replace("-".toRegex(), "")
        call_number = call_number!!.replace(" ".toRegex(), "")
        val clip = ClipData.newPlainText("Clipboard", call_number)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(applicationContext, "Номер скопирован!", Toast.LENGTH_SHORT).show()
    }
    val data: Unit
        get() {
            for (i in 0..2) {
                val dataMApi = HashMap<String, String>()
                dataMApi["Title"] = "Zimbabwe"
                dataMApi["free_message_header"] = "AUTORUS"
                dataMApi["free_message_text"] = "Спасибо за регистрацию в AUTORUS! Ваш проверочный код: 125854. Никому не сообщайте этот код!"
                dataMApi["free_time_remained"] = "5 минут назад"
                dataMApi["color"] = baseColor.toString()
                dataList!!.add(dataMApi)
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (parentActivityIntent == null) {
                    Log.i(TAG, "You have forgotten to specify the parentActivityName in the AndroidManifest!")
                    onBackPressed()
                } else {
                    NavUtils.navigateUpFromSameTask(this)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    internal inner class AsyncParse : AsyncTask<Void?, Void?, Void?>() {
//        override fun doInBackground(vararg params: Void?): Void? {
//            var numbers_data_list: ArrayList<ArrayList<String?>?>
//            val cn = call_number!!.replace("-", "").replace(" ", "").replace("+", "")
//            try {
//                parser!!.parse_messages(origin!!, cn)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return null
//        }
//
//        override fun onPreExecute() {
//            shimmer_free_rent_info_layout!!.visibility = View.VISIBLE
//            free_messages_recycle_view!!.visibility = View.GONE
//            Log.d(TAG, "ON PRE EXECUTE")
//            parser!!.messagesList.clear()
//            super.onPreExecute()
//        }
//
//        override fun onPostExecute(result: Void?) {
//            dataList!!.clear()
//            shimmer_free_rent_info_layout!!.visibility = View.GONE
//            free_messages_recycle_view!!.visibility = View.VISIBLE
//            for ((message_title, message_text, time_remained) in parser!!.messagesList) {
//                val dataMApi = HashMap<String, String>()
//                dataMApi["Title"] = "NoTitle"
//                dataMApi["free_message_header"] = message_title
//                dataMApi["free_message_text"] = message_text
//                dataMApi["free_time_remained"] = time_remained
//                dataMApi["color"] = baseColor.toString()
//                dataList!!.add(dataMApi)
//            }
//
////            getData();
//            val adapter = FreeMessagesDataAdapter(this@FreeRentInfoActivity, dataList)
//            free_messages_recycle_view!!.adapter = adapter
//            super.onPostExecute(result)
//        }
//    }

    private fun getBitmap(drawableRes: Int): Bitmap {
        val drawable = resources.getDrawable(drawableRes)
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)

        return bitmap
    }
}