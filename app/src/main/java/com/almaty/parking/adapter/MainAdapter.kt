package com.almaty.parking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.almaty.parking.R
import com.google.firebase.auth.FirebaseAuth

class MainAdapter(private val list: List<Items>, var context: Context): RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    class ViewHolder (ItemView: View):RecyclerView.ViewHolder(ItemView){
        val mestoParkinga: TextView = itemView.findViewById(R.id.tvEtaj)
        val status: TextView = itemView.findViewById(R.id.tvStatus)
        val btn:Button = itemView.findViewById(R.id.btnBron)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.parking_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        val items = list[position]
        holder.mestoParkinga.text = "${items.etaj} этаж, ${items.number} место"
        if (items.car_number.isEmpty()){
            holder.status.text = "Свободна"
        }
        else{
            holder.status.text = "забронировано на номер ${items.car_number}"
            if (FirebaseAuth.getInstance().currentUser?.email != items.userEmail){
                holder.btn.isVisible = false
            }
            holder.btn.text = "освобождать"
        }

        holder.btn.setOnClickListener {
            
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}