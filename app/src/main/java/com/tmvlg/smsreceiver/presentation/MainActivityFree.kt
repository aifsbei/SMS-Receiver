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

class MainActivityFree : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, KodeinAware {
    var freeRentFragment = FreeRentFragment()
    var aboutFragment = AboutFragment()

    private var nightMode: Int? = null

    override val kodein by closestKodein()

    private val settingsPreferenceProvider: SettingsPreferenceProvider by instance()

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("1", "task: start activity")

        preSetTheme()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_free)

        nightMode = AppCompatDelegate.getDefaultNightMode()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val previousNightMode = savedInstanceState?.getInt("theme")
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        if (previousNightMode != null && previousNightMode != currentNightMode) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_about)
            return
        }

        Log.d("1", "task: end activity")

        setupAds()
        setupFade()
        bottomNavigationView.setSelectedItemId(R.id.navigation_free_rent)


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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_free_rent -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, freeRentFragment).commit()
                return true
            }
            R.id.navigation_about -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, aboutFragment).commit()
                return true
            }
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentNightMode = nightMode
        outState.putInt("theme", currentNightMode?:-100)
    }

    private fun preSetTheme() {
        Log.d("1", "task: preSetTheme")
        val themeSet = settingsPreferenceProvider.isThemeSet()

        if (!themeSet) {
            val isSystemThemeDark = this.isDarkThemeOn()
            settingsPreferenceProvider.saveSettings(isSystemThemeDark, null)
        }

        val darkMode = settingsPreferenceProvider.getSettings().get(SettingsPreferenceProvider.KEY_DARK_THEME_ENABLED) ?: false

        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setupFade() {
        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade
    }

    private fun setupAds() {
        MobileAds.initialize(this) {}
    }

}