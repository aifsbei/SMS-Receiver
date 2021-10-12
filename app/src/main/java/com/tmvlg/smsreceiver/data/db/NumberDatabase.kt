package com.tmvlg.smsreceiver.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRent

@Database(
    entities = [NumberForRent::class],
    version = 1
)

abstract class NumberDatabase: RoomDatabase() {
    abstract fun numberForRentDao(): NumberForRentDao

    companion object {

        @Volatile private var instance: NumberDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                NumberDatabase::class.java,
                "number.db")
                .build()
    }
}