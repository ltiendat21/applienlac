package com.example.app_contacts.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_contacts.models.ContactModel

@Database(entities = [ContactModel::class], version = 1)
abstract class ContactsDatabase: RoomDatabase() {
    abstract fun contactDAO(): ContactDAO

    companion object {
        //database or database name
        private const val DATABASE_NAME: String = "CONTACT_DATABASE"

        private var instance: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java, DATABASE_NAME
                ).allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}