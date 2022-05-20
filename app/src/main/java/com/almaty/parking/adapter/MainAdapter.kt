package com.almaty.parking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.almaty.parking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainAdapter(private val list: List<Items>, var context: Context): RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    var db = FirebaseFirestore.getInstance()

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
        if (items.userEmail.isEmpty()){
            holder.status.text = "Свободна"
        }
        else{

            if (FirebaseAuth.getInstance().currentUser?.email != items.userEmail){
                holder.status.text = "забронировано на номер ${items.car_number}"
                holder.btn.visibility = View.GONE

            }
            else{
                holder.status.text = "вы забронировали"
                holder.btn.text = "освобождать"
            }

        }

        holder.btn.setOnClickListener {
            if(items.userEmail.isEmpty()){
                Toast.makeText(context, "Забронрирован", Toast.LENGTH_LONG).show()
                db.collection("Parking")
                    .document("g1pbnl1IFTQi8ZNK0Axs")
                    .update("car_number","232CCC08")
                db.collection("Parking")
                    .document("g1pbnl1IFTQi8ZNK0Axs")
                    .update("userEmail","ushkempire@gmail.com")
            }
            else{
                Toast.makeText(context, "Освободили", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}