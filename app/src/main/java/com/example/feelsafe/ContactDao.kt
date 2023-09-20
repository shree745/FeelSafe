package com.example.feelsafe

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ContactDao {

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    suspend  fun insert(contactModel: ContactModel)

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    suspend  fun insertAll(contactModel: List<ContactModel>)

    @Query("SELECT * FROM contactmodel")
    fun getAllContacts() : LiveData<List<ContactModel>>
}