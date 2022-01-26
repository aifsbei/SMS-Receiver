package com.tmvlg.smsreceiver.presentation.about

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.data.preferences.SettingsPreferenceProvider
import com.tmvlg.smsreceiver.databinding.FragmentAboutBinding
import com.tmvlg.smsreceiver.presentation.MainActivity
import com.tmvlg.smsreceiver.presentation.MainActivityFree
import com.tmvlg.smsreceiver.presentation.howtouse.HowToUseActivity
import com.tmvlg.smsreceiver.presentation.howtouse.HowToUseFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AboutFragment : Fragment(), KodeinAware {


    private var _binding: FragmentAboutBinding? = null
    private val binding: FragmentAboutBinding
        get() = _binding ?: throw IllegalStateException("null binding at $this")

    private lateinit var viewModel: AboutViewModel

    override val kodein by closestKodein()

    private val factory by instance<AboutViewModelFactory>()

    private var themeSystemCheck = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, factory)[AboutViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        viewModel.loadSettings()

        binding.contactLayout.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "d4nila.leskov@gmail.com", null
            ))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SMS Receiver")
            startActivity(Intent.createChooser(emailIntent, "Select app to contact us"))
        }

        binding.rateLayout.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=PackageName")))
        }
        binding.howToUseLayout.setOnClickListener {
            val intent = Intent(requireActivity(), HowToUseActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
        }

    }

    private fun observeViewModel() {
        viewModel.darkThemeSelected.observe(viewLifecycleOwner) {
            binding.darkThemeSwitch.isChecked = it

            binding.darkThemeSwitch.jumpDrawablesToCurrentState()

            viewModel.darkThemeSelected.removeObserver { viewLifecycleOwner }

            binding.darkThemeSwitch.setOnCheckedChangeListener { compoundButton, b ->
                viewModel.saveDarkThemeSelected(b)
                val theme = when (b) {
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(theme)
            }
        }
        viewModel.copySettingsSelected.observe(viewLifecycleOwner) {
            binding.copyPrefixSwitch.isChecked = it
            binding.copyPrefixSwitch.jumpDrawablesToCurrentState()

            viewModel.copySettingsSelected.removeObserver { viewLifecycleOwner }

            binding.copyPrefixSwitch.setOnCheckedChangeListener { compoundButton, b ->
                viewModel.saveCopySettingsSelected(b)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "1"
        fun newInstance() = AboutFragment()
    }
}