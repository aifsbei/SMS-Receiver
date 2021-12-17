package com.tmvlg.smsreceiver.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmvlg.smsreceiver.domain.numberforrent.NumberForRent

@Dao
interface NumberForRentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(numberForRent: NumberForRent)

    @Query("DELETE FROM number_for_rent")
    fun deleteAll()

    @Query("SELECT * FROM number_for_rent")
    fun getNumbers(): LiveData<List<NumberForRent>>

}