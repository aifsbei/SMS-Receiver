package com.tmvlg.smsreceiver

import android.app.Application
import com.tmvlg.smsreceiver.data.db.NumberDatabase
import com.tmvlg.smsreceiver.data.freemessage.FreeMessageRepositoryImpl
import com.tmvlg.smsreceiver.data.freenumber.FreeNumberRepositoryImpl
import com.tmvlg.smsreceiver.data.network.ConnectivityInterceptor
import com.tmvlg.smsreceiver.data.network.ConnectivityInterceptorImpl
import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.old.OnlineSimApiServiceOld
import com.tmvlg.smsreceiver.data.number.NumberForRentRepositoryImpl
import com.tmvlg.smsreceiver.data.preferences.SettingsPreferenceProvider
import com.tmvlg.smsreceiver.data.settings.SettingsRepositoryImpl
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessageRepository
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import com.tmvlg.smsreceiver.domain.settings.SettingsRepository
import com.tmvlg.smsreceiver.presentation.about.AboutViewModelFactory
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentDetailViewModelFactory
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentViewModelFactory
import com.tmvlg.smsreceiver.presentation.rent.RentNumberViewModelFactory
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectcountry.search.SearchCountryViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import androidx.appcompat.app.AppCompatDelegate

import android.util.Log
import com.tmvlg.smsreceiver.data.NumberDataSource
import com.tmvlg.smsreceiver.data.network.freenumbersapi.onlinesimfree.OnlineSimFreeApi
import com.tmvlg.smsreceiver.data.network.rentnumbersapi.onlinesim.OnlineSimApi
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.SelectServiceViewModelFactory
import com.tmvlg.smsreceiver.presentation.rent.newnumber.pager.selectservice.search.SearchServiceViewModelFactory


class SMSReceiverApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@SMSReceiverApplication))
        bind() from singleton { OnlineSimApi }
        bind() from singleton { OnlineSimFreeApi }
        bind() from singleton { NumberDatabase(instance()) }
        bind() from singleton { instance<NumberDatabase>().numberForRentDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OnlineSimApiServiceOld(instance()) }

        bind() from singleton { SettingsPreferenceProvider(instance()) }
        bind<SettingsRepository>() with singleton { SettingsRepositoryImpl(instance()) }
        bind() from provider { AboutViewModelFactory(instance()) }

        bind() from singleton { NumberDataSource(instance()) }
        bind<NumberForRentRepository>() with singleton { NumberForRentRepositoryImpl(instance(), instance()) }
        bind() from provider { RentNumberViewModelFactory(instance()) }
        bind() from provider { SearchCountryViewModelFactory(instance()) }
        bind() from provider { SearchServiceViewModelFactory(instance()) }
        bind() from provider { SelectServiceViewModelFactory(instance()) }

        bind<FreeNumberRepository>() with singleton { FreeNumberRepositoryImpl }
        bind() from provider { FreeRentViewModelFactory(instance()) }

        bind<FreeMessageRepository>() with singleton { FreeMessageRepositoryImpl }
        bind() from provider { FreeRentDetailViewModelFactory(instance(), instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()

        Log.d("1", "task: application")

        applyDayNightMode()
    }

    fun applyDayNightMode() {
        var mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        val settingsPreferenceProvider by instance<SettingsPreferenceProvider>()

        if (settingsPreferenceProvider.isThemeSet()) {

            val darkMode = settingsPreferenceProvider.getSettings()
                .get(SettingsPreferenceProvider.KEY_DARK_THEME_ENABLED) ?: false

            if (darkMode) {
                mode = AppCompatDelegate.MODE_NIGHT_YES
            } else {
                mode = AppCompatDelegate.MODE_NIGHT_NO
            }
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }


}