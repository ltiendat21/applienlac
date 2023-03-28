package com.example.app_contacts.activity

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_contacts.adapters.AdapterContact
import com.example.app_contacts.databinding.ActivitySearchBinding
import com.example.app_contacts.storage.ContactDAO
import com.example.app_contacts.storage.ContactsDatabase

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var contactDAO: ContactDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionToolBar()
        setSearchView()
    }

    private fun setSearchView() {
        contactDAO = ContactsDatabase.getInstance(applicationContext)?.contactDAO()!!
        val adapter = AdapterContact(contactDAO.selectAll())
        binding.rvResult.adapter = adapter
        binding.rvResult.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val newList = contactDAO.selectAll().filter {
                    it.name.lowercase().contains(newText!!.lowercase()) || it.phoneNumber.contains(
                        newText
                    )
                }
                adapter.setItems(newList)
                return true
            }
        })

        binding.searchView.onActionViewExpanded()
    }

    private fun actionToolBar() {
        binding.imgBtnBack.setOnClickListener {
            finish()
        }
    }
}