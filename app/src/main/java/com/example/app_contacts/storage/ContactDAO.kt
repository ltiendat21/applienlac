package com.example.app_contacts.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app_contacts.models.ContactModel

@Dao
interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: ContactModel)

    @Query("DELETE FROM ContactModel WHERE name = :name")
    fun deleteByName(name: String)

    @Query("UPDATE ContactModel SET name = :name, phoneNumber = :phone, email = :email, address = :address, note = :note WHERE name = :name")
    fun updateByName(name: String, phone: String, email: String, address: String, note: String)

    @Query("SELECT * FROM ContactModel ORDER BY name")
    fun selectAll(): List<ContactModel>
}