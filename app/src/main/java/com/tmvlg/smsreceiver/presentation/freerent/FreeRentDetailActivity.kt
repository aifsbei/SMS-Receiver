package com.tmvlg.smsreceiver.presentation.freerent

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
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
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.lang.RuntimeException
import java.util.*

class FreeRentDetailActivity : AppCompatActivity(), KodeinAware {
    var TAG = "1"
    var arrow_back: ImageView? = null
    var free_country_flag: ImageView? = null
    var free_country_flag_overlay: ImageView? = null
    var free_region_code_info: TextView? = null
    var free_country_name_info: TextView? = null
    var free_call_number_info: TextView? = null
    var free_call_prefix_info: TextView? = null
    var refresh_button: MaterialButton? = null
    var substrate_countryname: View? = null
    var substrate_callnumber: View? = null
    var free_copy_icon_info: ImageView? = null
    var free_country_flag_shadow: ImageView? = null
    var baseColor = 0
    var textColor = 0
    var origin: String? = null
    var call_number_extended: String? = null
    var dataList: ArrayList<HashMap<String, String>>? = null
    var free_messages_recycler_view: RecyclerView? = null
    var shimmer_free_rent_info_layout: ShimmerFrameLayout? = null
    var linearLayoutManager: LinearLayoutManager? = null


    private lateinit var viewModel: FreeRentDetailViewModel
    private lateinit var freeMessageDataAdapter: FreeMessageDataAdapter
    private var freeNumberId: Int = FreeNumber.UNDEFINED_ID

    override val kodein by closestKodein()

    private val viewModelFactory by instance<FreeRentDetailViewModelFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_free_rent_detail)
        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade
        arrow_back = findViewById(R.id.arrow_back)
        free_country_flag = findViewById(R.id.free_country_flag)
        free_country_flag_overlay = findViewById(R.id.free_country_flag_overlay)
        free_region_code_info = findViewById(R.id.free_region_code_info)
        free_country_name_info = findViewById(R.id.free_country_name_info)
        free_call_number_info = findViewById(R.id.free_call_number_info)
        free_call_prefix_info = findViewById(R.id.free_call_prefix_info)
        refresh_button = findViewById(R.id.refresh_button)
        substrate_countryname = findViewById(R.id.substrate_countryname)
        substrate_callnumber = findViewById(R.id.substrate_callnumber)
        free_country_flag_shadow = findViewById(R.id.free_country_flag_shadow)
        free_copy_icon_info = findViewById(R.id.free_copy_icon_info)

        free_messages_recycler_view = findViewById(R.id.free_messages_recycle_view)
        shimmer_free_rent_info_layout = findViewById(R.id.shimmer_free_rent_info_layout)
        linearLayoutManager = LinearLayoutManager(this)
        free_messages_recycler_view?.setLayoutManager(linearLayoutManager)

        val toolbar = findViewById<View>(R.id.free_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Log.d(TAG, "problem: CREATE VIEW")
        shimmer_free_rent_info_layout!!.visibility = View.VISIBLE
        free_messages_recycler_view!!.visibility = View.GONE



        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(FreeRentDetailViewModel::class.java)

        parseIntent()

        viewModel.initMessageRepository(freeNumberId)

        setupRecyclerView()
        init_data()

        viewModel.messageList.observe(this) {
            Log.d(TAG, "problem: INITED")
            Log.d(TAG, "problem: ${it.size}")
            freeMessageDataAdapter.submitList(it)
            shimmer_free_rent_info_layout!!.visibility = View.GONE
            free_messages_recycler_view!!.visibility = View.VISIBLE
        }




        refresh_button?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                viewModel.initMessageRepository(freeNumberId)
            }
        })

        arrow_back?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                super@FreeRentDetailActivity.finish()
            }
        })

        free_copy_icon_info?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                copyNumber()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearList()
    }

    fun parseIntent() {
        if (!intent.hasExtra(EXTRA_FREE_NUMBER_ID)) {
            throw RuntimeException("No EXTRA_FREE_NUMBER_ID found!")
        }
        freeNumberId = intent.getIntExtra(EXTRA_FREE_NUMBER_ID, FreeNumber.UNDEFINED_ID)
    }

    fun setupRecyclerView() {
        freeMessageDataAdapter = FreeMessageDataAdapter()
        free_messages_recycler_view?.adapter = freeMessageDataAdapter
    }


    fun init_data() {
        val freeNumber = viewModel.getFreeNumber(freeNumberId)
        call_number_extended = freeNumber.call_number
        call_number_extended = call_number_extended!!.replace("-", "");
        call_number_extended = call_number_extended!!.replace(" ", "");
        val icon_path = freeNumber.icon_path.lowercase().substring(0, 2)
        val flag_resID = this.resources.getIdentifier(icon_path,
                "xml",
                this.packageName)
        val srcBitmap = getBitmap(flag_resID)
        baseColor = ContextCompat.getColor(this, R.color.design_default_color_background)
        textColor = ContextCompat.getColor(this, R.color.design_default_color_secondary_variant)

        val palette = Palette.from(srcBitmap).generate()
        var vibrantSwatch = palette.mutedSwatch
        if (vibrantSwatch == null) {
            vibrantSwatch = palette.vibrantSwatch
        }
        baseColor = vibrantSwatch!!.rgb
        textColor = vibrantSwatch.titleTextColor
        Log.d(TAG, "baseColor = $baseColor")
        Log.d(TAG, "textColor = $textColor")

        freeMessageDataAdapter.baseColor = baseColor

        /*SETTING FLAG*/
        free_country_flag!!.setImageResource(flag_resID)

        /*FLAG OVERLAY*/
        free_country_flag_overlay!!.setBackgroundColor(baseColor)

        /*SUBSTRATE COUNTRYNAME*/
        substrate_countryname!!.setBackgroundColor(baseColor)

        /*SUBSTRATE CALLNUMBER*/
        substrate_callnumber!!.setBackgroundColor(baseColor)

        /*REFRESH BUTTON*/
        refresh_button!!.setBackgroundColor(baseColor)
        refresh_button!!.rippleColor = ColorStateList.valueOf(Color.rgb(228, 228, 228))

        /*CODE*/
        free_region_code_info!!.text = freeNumber.country_code

        /*PREFIX*/
        free_call_prefix_info!!.text = freeNumber.call_number_prefix

        /*NUMBER*/
        free_call_number_info!!.text = call_number_extended

        /*COUNTRY*/
        free_country_name_info!!.text = freeNumber.counrty_name
    }

    fun copyNumber() {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Clipboard", call_number_extended)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(applicationContext, "Номер скопирован!", Toast.LENGTH_SHORT).show()
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

    private fun getBitmap(drawableRes: Int): Bitmap {
        val drawable = resources.getDrawable(drawableRes)
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)

        return bitmap
    }

    companion object {
        private const val EXTRA_FREE_NUMBER_ID = "extra_free_number_id"

        fun newIntentShowDetail(context: Context, freeNumberId: Int): Intent {
            val intent = Intent(context, FreeRentDetailActivity::class.java)
            intent.putExtra(EXTRA_FREE_NUMBER_ID, freeNumberId)
//            intent.putExtra("free_region_icon", dataMap["free_region_icon"])
//            intent.putExtra("free_prefix", dataMap["free_prefix"])
//            intent.putExtra("free_call_number", dataMap["free_call_number"])
//            intent.putExtra("origin", dataMap["origin"])
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation((context as Activity), free_region_icon, "free_flag_transition")
//            startActivity(intent, options.toBundle())
            return intent
        }
    }
}