package com.tmvlg.smsreceiver

import android.app.Application
import com.tmvlg.smsreceiver.data.db.NumberDatabase
import com.tmvlg.smsreceiver.data.db.NumberForRentDao
import com.tmvlg.smsreceiver.data.freemessage.FreeMessageRepositoryImpl
import com.tmvlg.smsreceiver.data.freenumber.FreeNumberRepositoryImpl
import com.tmvlg.smsreceiver.data.network.ConnectivityInterceptor
import com.tmvlg.smsreceiver.data.network.ConnectivityInterceptorImpl
import com.tmvlg.smsreceiver.data.network.onlinesim.OnlineSimApiService
import com.tmvlg.smsreceiver.data.number.NumberForRentRepositoryImpl
import com.tmvlg.smsreceiver.domain.freemessage.FreeMessageRepository
import com.tmvlg.smsreceiver.domain.freenumber.FreeNumberRepository
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRentRepository
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentDetailViewModelFactory
import com.tmvlg.smsreceiver.presentation.freerent.FreeRentViewModelFactory
import com.tmvlg.smsreceiver.presentation.rent.RentNumberViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class SMSReceiverApplication: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@SMSReceiverApplication))
        bind() from singleton { NumberDatabase(instance()) }
        bind() from singleton { instance<NumberDatabase>().numberForRentDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OnlineSimApiService(instance()) }
        bind<NumberForRentRepository>() with singleton { NumberForRentRepositoryImpl(instance()) }
        bind() from provider { RentNumberViewModelFactory(instance()) }

        bind<FreeNumberRepository>() with singleton { FreeNumberRepositoryImpl }
        bind() from provider { FreeRentViewModelFactory(instance()) }

        bind<FreeMessageRepository>() with singleton { FreeMessageRepositoryImpl }
        bind() from provider { FreeRentDetailViewModelFactory(instance(), instance()) }
    }

}