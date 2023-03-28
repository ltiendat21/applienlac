package com.example.app_contacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_contacts.R
import com.example.app_contacts.models.ListContactByCharModel

class AdapterListContactByChar(private var listContact: List<ListContactByCharModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ItemContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvChar: TextView
        var rcvList: RecyclerView

        init {
            tvChar = itemView.findViewById(R.id.tvByChar)
            rcvList = itemView.findViewById(R.id.rcvListByChar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_contact_by_char, parent, false)
        return ItemContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemContactViewHolder)
        val itemListContact = listContact[position]

        holder.apply {
            tvChar.text = itemListContact.char

            val adapter = AdapterContact(itemListContact.contacts)
            rcvList.adapter = adapter
            rcvList.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
        }
    }

    override fun getItemCount(): Int = listContact.size
}