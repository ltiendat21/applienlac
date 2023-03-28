package com.example.app_contacts.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_contacts.adapters.AdapterContact
import com.example.app_contacts.adapters.AdapterListContactByChar
import com.example.app_contacts.databinding.ActivityMainBinding
import com.example.app_contacts.models.ContactModel
import com.example.app_contacts.models.ListContactByCharModel
import com.example.app_contacts.storage.ContactsDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionToolBar()
        setListContact()
    }

    private fun actionToolBar() {
        binding.imgBtnAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddActivity::class.java))
        }
        binding.imgBtnSearch.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

    private fun setListContact() {
        val contactDAO = ContactsDatabase.getInstance(applicationContext)?.contactDAO()!!
        binding.rcvListContactByChar.adapter =
            AdapterListContactByChar(convertToContactListByChar(contactDAO.selectAll()))
        binding.rcvListContactByChar.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
    }

    private fun convertToContactListByChar(contactList: List<ContactModel>): List<ListContactByCharModel> {
        val result = ('A'..'Z').map { ListContactByCharModel(it.toString(), emptyList()) }
        for (contact in contactList) {
            val firstChar = contact.name.firstOrNull()?.toUpperCase().toString()
            val list = result.firstOrNull { it.char == firstChar }?.contacts?.toMutableList()
                ?: mutableListOf()
            list.add(contact)
            result.firstOrNull { it.char == firstChar }?.contacts = list
        }
        return result.filter { it.contacts.isNotEmpty() }
    }

    override fun onResume() {
        super.onResume()
        setListContact()
    }
}