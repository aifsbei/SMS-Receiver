package com.tmvlg.smsreceiver.presentation

import android.os.Bundle
import android.transition.Fade
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tmvlg.smsreceiver.R
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    var freeRentFragment = FreeRentFragment()
    var profileFragment = ProfileFragment()
    var rentNumberFragment = RentNumberFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        getSupportActionBar().hide();
        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.setSelectedItemId(R.id.navigation_rent_number)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_free_rent -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, freeRentFragment).commit()
                return true
            }
            R.id.navigation_rent_number -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, rentNumberFragment).commit()
                return true
            }
            R.id.navigation_profile -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, profileFragment).commit()
                return true
            }
        }
        return false
    }
}