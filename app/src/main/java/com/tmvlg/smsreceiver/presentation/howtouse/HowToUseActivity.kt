package com.tmvlg.smsreceiver.presentation.howtouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tmvlg.smsreceiver.R

class HowToUseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_use)

        supportFragmentManager.beginTransaction()
            .replace(R.id.how_to_use_container, HowToUseFragment())
            .commit()
    }
}