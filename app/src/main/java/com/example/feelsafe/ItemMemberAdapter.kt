package com.example.feelsafe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ItemMemberAdapter(private val listMembers: List<MemberModel>) : RecyclerView.Adapter<ItemMemberAdapter.ViewHolder>() {

    class ViewHolder(private val item: View): RecyclerView.ViewHolder(item) {
        //            val imgUser = item.findViewById<ImageView>(R.id.userImage)
        val userName = item.findViewById<TextView>(R.id.userName)
        val userAddress = item.findViewById<TextView>(R.id.address)
        val userBattery = item.findViewById<TextView>(R.id.battery_percent)
        val userDistance = item.findViewById<TextView>(R.id.distance_value)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_member,parent,false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return listMembers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listMembers[position]
        holder.userName.text = item.name
        holder.userAddress.text = item.address
        holder.userBattery.text = item.battery
        holder.userDistance.text = item.distance
    }
}