package com.almaty.parking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.almaty.parking.adapter.Items
import com.almaty.parking.adapter.MainAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.lang.String
import kotlin.toString


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    val dataArray = arrayListOf<Items>()
    var db = FirebaseFirestore.getInstance()
    lateinit var adapter:MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        showDate()
        adapter = MainAdapter(dataArray, context = applicationContext)
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showDate() {
        db.collection("Parking").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val data = Items(
                            document["etaj"].toString(),
                            document["number"].toString(),
                            document["car_number"].toString(),
                            document["userEmail"].toString()
                        )
                        dataArray.add(data)
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Error getting documents.", Toast.LENGTH_LONG).show()
                }
            })
    }
}