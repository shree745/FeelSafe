package com.example.feelsafe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InviteMemberAdapter(private val listContacts : List<ContactModel>) : RecyclerView.Adapter<InviteMemberAdapter.ViewHolder>() {
    class ViewHolder(private val item1: View): RecyclerView.ViewHolder(item1) {
        val contactname = item1.findViewById<TextView>(R.id.contact_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.invite_member,parent,false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return listContacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item1 = listContacts[position]
        holder.contactname.text = item1.cname
    }
}