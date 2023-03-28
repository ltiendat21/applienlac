package com.example.app_contacts.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class ContactModel(
    @PrimaryKey() val name: String,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "address") val address: String? = null,
    @ColumnInfo(name = "note") val note: String? = null,
) : Serializable