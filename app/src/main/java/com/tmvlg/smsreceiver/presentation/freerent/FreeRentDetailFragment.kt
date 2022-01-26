package com.tmvlg.smsreceiver.presentation.freerent

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.databinding.FragmentFreeRentDetailBinding
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumber
import com.tmvlg.smsreceiver.presentation.freerent.freemessagelist.FreeMessageDataAdapter
import com.tmvlg.smsreceiver.util.calculateAverageColor
import com.tmvlg.smsreceiver.util.getBitmap
import com.tmvlg.smsreceiver.util.isOnline
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import kotlin.reflect.typeOf

class FreeRentDetailFragment : Fragment(), KodeinAware {
    var baseColor = 0
    var textColor = 0
    var call_number_extended: String? = null


    private lateinit var viewModel: FreeRentDetailViewModel
    private lateinit var freeMessageDataAdapter: FreeMessageDataAdapter
    private var freeNumberId: Int = FreeNumber.UNDEFINED_ID

    override val kodein by closestKodein()

    private var _binding: FragmentFreeRentDetailBinding? = null
    private val binding: FragmentFreeRentDetailBinding
        get() = _binding ?: throw IllegalStateException("null binding at $this")

    private val viewModelFactory by instance<FreeRentDetailViewModelFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadState(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFreeRentDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[FreeRentDetailViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            loadState(savedInstanceState)
        }

        binding.freeMessagesRecycleView.layoutManager = LinearLayoutManager(requireContext())

//        binding.shimmerFreeRentInfoLayout.visibility = View.VISIBLE
        binding.freeMessagesRecycleView.visibility = View.GONE

        if (requireContext().isOnline()) {
            binding.shimmerFreeRentInfoLayout.visibility = View.VISIBLE
            viewModel.initMessageRepository(freeNumberId)
        }
        else {
            binding.noConnectionLayout.visibility = View.VISIBLE
            binding.shimmerFreeRentInfoLayout.visibility = View.INVISIBLE
        }

        setupRecyclerView()
        init_data()

        observeViewModel()

        binding.refreshButton.setOnClickListener {
            binding.freeMessagesRecycleView.visibility = View.GONE

            if (requireContext().isOnline()) {
                binding.noConnectionLayout.visibility = View.GONE
                binding.shimmerFreeRentInfoLayout.visibility = View.VISIBLE
                viewModel.initMessageRepository(freeNumberId)
            }
            else {
                binding.noConnectionLayout.visibility = View.VISIBLE
                binding.shimmerFreeRentInfoLayout.visibility = View.INVISIBLE
            }
        }

        binding.arrowBack.setOnClickListener {
            Log.d(TAG, "onViewCreated: POP")
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.freeCopyIconInfo.setOnClickListener {
            copyNumber()
        }
    }

    private fun observeViewModel() {
        viewModel.getFreeNumberException.observe(viewLifecycleOwner) { e ->
            if (e != null) {
                Toast.makeText(requireContext(), "Please, reload this page", Toast.LENGTH_SHORT)
                    .show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        viewModel.messageList.observe(viewLifecycleOwner) {
            freeMessageDataAdapter.submitList(it)
            if (it.isNotEmpty()) {
                binding.shimmerFreeRentInfoLayout.visibility = View.GONE
                binding.freeMessagesRecycleView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearList()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        saveState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun saveState(outState: Bundle) {
        outState.getInt(EXTRA_FREE_NUMBER_ID, freeNumberId)
    }

    private fun loadState(bundle: Bundle) {
        freeNumberId = bundle.getInt(EXTRA_FREE_NUMBER_ID)
            ?: throw IllegalArgumentException("Bundle must contain freeNumberId")
    }

    fun setupRecyclerView() {
        freeMessageDataAdapter = FreeMessageDataAdapter()
        binding.freeMessagesRecycleView.adapter = freeMessageDataAdapter
    }


    fun init_data() {
        val freeNumber = viewModel.getFreeNumber(freeNumberId) ?: return
        call_number_extended = freeNumber.call_number
        call_number_extended = call_number_extended!!.replace("-", "");
        call_number_extended = call_number_extended!!.replace(" ", "");
        val icon_path = freeNumber.icon_path.lowercase().substring(0, 2)
        val flag_resID = this.resources.getIdentifier(
            icon_path,
            "xml",
            requireActivity().packageName
        )
        val srcBitmap = requireContext().getBitmap(flag_resID)
        baseColor =
            ContextCompat.getColor(requireContext(), R.color.design_default_color_background)
        textColor =
            ContextCompat.getColor(requireContext(), R.color.design_default_color_secondary_variant)

        val darkMode = viewModel.isDarkTheme()

        val palette = Palette.from(srcBitmap).generate()
        val baseColor: Int = if (darkMode) {
            palette.lightVibrantSwatch?.rgb ?: ColorUtils.blendARGB(
                palette.vibrantSwatch?.rgb
                    ?: palette.lightMutedSwatch?.rgb
                    ?: palette.mutedSwatch?.rgb
                    ?: palette.darkVibrantSwatch?.rgb
                    ?: palette.darkMutedSwatch?.rgb
                    ?: palette.dominantSwatch?.rgb
                    ?: Color.BLACK,
                Color.WHITE,
                0.5F
            )
        } else {
            palette.dominantSwatch?.rgb
                ?: palette.vibrantSwatch?.rgb
//            ?: palette.dominantSwatch?.rgb
                ?: palette.lightMutedSwatch?.rgb
                ?: palette.lightVibrantSwatch?.rgb
                ?: palette.darkMutedSwatch?.rgb
                ?: palette.darkVibrantSwatch?.rgb
                ?: palette.mutedSwatch?.rgb
                ?: Color.GRAY
        }


        freeMessageDataAdapter.baseColor = baseColor

        /*SETTING FLAG*/
        binding.freeCountryFlag.setImageResource(flag_resID)

        /*FLAG OVERLAY*/
        binding.freeCountryFlagOverlay.setBackgroundColor(baseColor)

        /*SUBSTRATE COUNTRYNAME*/
        binding.substrateCountryname.setBackgroundColor(baseColor)

        /*SUBSTRATE CALLNUMBER*/
        binding.substrateCallnumber.setBackgroundColor(baseColor)

        /*REFRESH BUTTON*/
        binding.refreshButton.setBackgroundColor(baseColor)
        binding.refreshButton.rippleColor = ColorStateList.valueOf(Color.rgb(228, 228, 228))

        /*CODE*/
        binding.freeRegionCodeInfo.text = freeNumber.country_code

        /*PREFIX*/
        binding.freeCallPrefixInfo.text = freeNumber.call_number_prefix

        /*NUMBER*/
        binding.freeCallNumberInfo.text = call_number_extended

        /*COUNTRY*/
        binding.freeCountryNameInfo.text = freeNumber.counrty_name
    }

    fun copyNumber() {
        val clipData = if (viewModel.doCopyPrefix()) {
            binding.freeCallPrefixInfo.text.toString() + binding.freeCallNumberInfo.text.toString()
        } else {
            binding.freeCallNumberInfo.text
        }
        val clipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Clipboard", clipData)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), getString(R.string.copy_toast_text), Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val TAG = "1"
        private const val EXTRA_FREE_NUMBER_ID = "extra_free_number_id"

        fun newInstance(freeNumberId: Int): FreeRentDetailFragment {
            return FreeRentDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_FREE_NUMBER_ID, freeNumberId)
                }
            }
        }
    }
}