package com.tmvlg.smsreceiver.presentation

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.data.preferences.SettingsPreferenceProvider
import com.tmvlg.smsreceiver.presentation.about.AboutFragment
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentFragment
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

        val darkMode = settingsPreferenceProvider.getSettings().get(SettingsPreferenceProvider.KEY_DARK_THEME_ENABLED) ?: false

        if (darkMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

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

        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade
        bottomNavigationView.setSelectedItemId(R.id.navigation_free_rent)
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

}