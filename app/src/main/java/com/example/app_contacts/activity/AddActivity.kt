package com.example.app_contacts.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_contacts.databinding.ActivityAddBinding
import com.example.app_contacts.models.ContactModel
import com.example.app_contacts.storage.ContactDAO
import com.example.app_contacts.storage.ContactsDatabase

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var contactDAO: ContactDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editContact()
        actionToolBar()
        contactDAO = ContactsDatabase.getInstance(applicationContext)?.contactDAO()!!
    }

    private fun saveContact() {
        val name = binding.edtName.text.trim().toString()
        val phone = binding.edtPhoneNumber.text.trim().toString()
        val email = binding.edtEmail.text.trim().toString()
        val address = binding.edtAddress.text.trim().toString()
        val note = binding.edtNote.text.trim().toString()
        if (phone.isNotEmpty() && name.isNotEmpty()) {
            if (intent.hasExtra("contact")) {
                contactDAO.updateByName(name, phone, email, address, note)
            } else {
                contactDAO.insert(ContactModel(name, phone, email, address, note))
            }
            Toast.makeText(applicationContext, "Đã lưu vào danh bạ", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                applicationContext,
                "Không đủ thông tin để lưu. Đã hủy danh bạ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun editContact() {
        if (intent != null) {
            if (intent.hasExtra("contact")) {
                val contact = intent.getSerializableExtra("contact") as ContactModel
                binding.edtName.setText(contact.name)
                binding.edtPhoneNumber.setText(contact.phoneNumber)
                binding.edtEmail.setText(contact.email)
                binding.edtAddress.setText(contact.address)
                binding.edtNote.setText(contact.note)
            }
        }
    }

    private fun actionToolBar() {
        binding.imgBtnBack.setOnClickListener {
            finish()
        }
        binding.buttonSave.setOnClickListener {
            saveContact()
            finish()
        }
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

}