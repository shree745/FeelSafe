package com.example.feelsafe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactModel (
    val cname : String,
    @PrimaryKey
    val number : String
)