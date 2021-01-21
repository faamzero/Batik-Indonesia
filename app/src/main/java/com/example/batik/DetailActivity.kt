package com.example.batik

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.batik.databinding.ActivityDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailActivity : AppCompatActivity() {
    private lateinit var bindingActivityDetail: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivityDetail = ActivityDetailBinding.inflate(layoutInflater)
        val view = bindingActivityDetail.root
        setContentView(view)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("batik").child(
                key.toString()
        )

        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val mData: Data? = dataSnapshot.getValue(Data::class.java)
                if (mData != null) {
                    bindingActivityDetail.nameTextView.text = mData.name.toString()
                    bindingActivityDetail.dateTextView.text = mData.date.toString()
                    bindingActivityDetail.asalTextView.text = mData.asal.toString() + ""
                    bindingActivityDetail.descriptionTextView.text = mData.description.toString()
                    Glide.with(view)
                            .load(mData.url.toString())
                            .into(bindingActivityDetail.posterImgeView)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}