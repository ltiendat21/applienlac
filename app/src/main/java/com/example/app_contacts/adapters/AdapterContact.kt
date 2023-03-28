package com.example.app_contacts.adapters

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.app_contacts.R
import com.example.app_contacts.activity.AddActivity
import com.example.app_contacts.models.ContactModel
import com.example.app_contacts.storage.ContactsDatabase
import java.util.*

class AdapterContact(private var list: List<ContactModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ItemContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layoutItem: ConstraintLayout
        var imgAvatar: ImageView
        var tvAvatarByChar: TextView
        var tvName: TextView
        var tvPhoneNumberSearch: TextView
        var imgDelete: ImageView
        var layoutAction: LinearLayout
        var tvPhoneNumber: TextView
        var imgGoToCall: ImageView
        var imgGoToChat: ImageView
        var imgInfo: ImageView
        var viewDivider: View

        init {
            layoutItem = itemView.findViewById(R.id.layoutItem)
            imgAvatar = itemView.findViewById(R.id.imgAvatar)
            tvAvatarByChar = itemView.findViewById(R.id.tvAvatarByChar)
            tvName = itemView.findViewById(R.id.tvName)
            tvPhoneNumberSearch = itemView.findViewById(R.id.tvPhoneNumberSearch)
            imgDelete = itemView.findViewById(R.id.imgDelete)
            layoutAction = itemView.findViewById(R.id.layoutAction)
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber)
            imgGoToCall = itemView.findViewById(R.id.imgGoToCall)
            imgInfo = itemView.findViewById(R.id.imgEdit)
            imgGoToChat = itemView.findViewById(R.id.imgGoToChat)
            viewDivider = itemView.findViewById(R.id.viewDivider)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ItemContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemContactViewHolder)
        val item = list[position]

        val firstChar = item.name.substring(0, 1).uppercase()
        holder.apply {
            imgDelete.visibility = View.INVISIBLE
            layoutAction.visibility = View.GONE

            tvName.text = item.name
            tvPhoneNumber.text = "Di động: ${item.phoneNumber}"
            tvPhoneNumberSearch.text = item.phoneNumber
            tvAvatarByChar.text = firstChar
            imgAvatar.background = setColorBackgroundRandom()
            viewDivider.visibility = if(position == list.size -1){
                View.GONE
            } else {
                View.VISIBLE
            }

            layoutItem.setOnClickListener {
                if (layoutAction.isVisible) {
                    layoutAction.visibility = View.GONE
                    imgDelete.visibility = View.INVISIBLE
                    viewDivider.visibility = if(position == list.size -1){
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                } else {
                    layoutAction.visibility = View.VISIBLE
                    imgDelete.visibility = View.VISIBLE
                    viewDivider.visibility = View.VISIBLE
                }
            }

            imgGoToCall.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.phoneNumber, null))
                imgGoToCall.context.startActivity(intent)
            }

            imgGoToChat.setOnClickListener {
                val smsIntent = Intent(Intent.ACTION_VIEW)
                smsIntent.data = Uri.parse("smsto:")
//                smsIntent.type = "vnd.android-dir/mms-sms"
                smsIntent.putExtra("address", item.phoneNumber)
                imgGoToCall.context.startActivity(smsIntent)
            }

            imgInfo.setOnClickListener {
                val intent = Intent(imgInfo.context, AddActivity::class.java)
                intent.putExtra("contact", item)
                imgInfo.context.startActivity(intent)
            }

            imgDelete.setOnClickListener {
                val contactDAO =
                    ContactsDatabase.getInstance(itemView.context)?.contactDAO()!!
                contactDAO.deleteByName(item.name)
                val newList = list as MutableList
                newList.removeAt(position)
                setItems(newList)
                Toast.makeText(itemView.context, "Đã xóa", Toast.LENGTH_SHORT).show()
                imgDelete.visibility = View.INVISIBLE
            }
        }
    }

    fun setItems(newList: List<ContactModel>) {
        list = newList
        notifyDataSetChanged()

    }

    private fun setColorBackgroundRandom(): GradientDrawable {
        val r = Random()
        val red = r.nextInt(255 - 0 + 1) + 0
        val green = r.nextInt(255 - 0 + 1) + 0
        val blue = r.nextInt(255 - 0 + 1) + 0

        val draw = GradientDrawable()
        draw.shape = GradientDrawable.OVAL
        draw.setColor(Color.rgb(red, green, blue))
        return draw
    }

    override fun getItemCount(): Int = list.size
}