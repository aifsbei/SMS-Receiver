package com.tmvlg.smsreceiver.presentation

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.data.preferences.SettingsPreferenceProvider
import com.tmvlg.smsreceiver.presentation.about.AboutFragment
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentFragment
import com.tmvlg.smsreceiver.presentation.howtouse.HowToUseActivity
import com.tmvlg.smsreceiver.util.isDarkThemeOn
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivityFree : AppCompatActivity(), KodeinAware {
    var freeRentFragment = FreeRentFragment()
    var aboutFragment = AboutFragment()

    private var nightMode: Int? = null

    override val kodein by closestKodein()

    private val settingsPreferenceProvider: SettingsPreferenceProvider by instance()

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("1", "task: start activity")

//        preSetTheme()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_free)


        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_free_rent -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, freeRentFragment).commit()
                }
                R.id.navigation_about -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, aboutFragment).commit()
                }
            }
            true
        }

        setupAds()
        setupFade()

        if (hasThemeChanged(savedInstanceState)) {
            bottomNavigationView.selectedItemId = R.id.navigation_about
            return
        }

        bottomNavigationView.selectedItemId = R.id.navigation_free_rent

        Log.d("1", "task: end activity")

    }

    override fun onStart() {
        super.onStart()
        Log.d("1", "task: onStart")
        if (settingsPreferenceProvider.getHowToUseState() == SettingsPreferenceProvider.STATE_NOT_SHOWN) {
            settingsPreferenceProvider.saveHowToUseState(SettingsPreferenceProvider.STATE_SHOWN)
            val intent = Intent(this, HowToUseActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentNightMode = nightMode
        outState.putInt(THEME_ID, currentNightMode ?: UNDEFINED_THEME)
    }

    private fun hasThemeChanged(savedInstanceState: Bundle?): Boolean {
        nightMode = AppCompatDelegate.getDefaultNightMode()
        val previousNightMode = savedInstanceState?.getInt(THEME_ID)
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        if (previousNightMode != null && previousNightMode != currentNightMode) {
            return true
        }
        return false
    }


    private fun setupFade() {
        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade
    }

    private fun setupAds() {
        MobileAds.initialize(this) {}
    }

    companion object {
        const val THEME_ID = "themeId"
        const val UNDEFINED_THEME = -100
    }

}