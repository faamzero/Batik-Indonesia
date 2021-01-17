package com.example.batik

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.batik.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {

    private lateinit var bindingActivityMain: ActivityMainBinding
    private lateinit var messagesListener: ValueEventListener

//    tulis pesan ke database
    private val database = Firebase.database
    private val listData:MutableList<Data> = ArrayList()
    private val myRef = database.getReference("batik")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivityMain = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingActivityMain.root
        setContentView(view)

        bindingActivityMain.addImageView.setOnClickListener { v ->
            val intent = Intent(this, AddActivity::class.java)
            v.context.startActivity(intent)
        }

        listData.clear()
        setupRecyclerView(bindingActivityMain.recyclerView)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        messagesListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listData.clear()
                dataSnapshot.children.forEach { resp ->
                    val mVideoBatik =
                            Data(resp.child("name").value as String?,
                                    resp.child("date").value as String?,
                                    resp.child("asal").value as String?,
                                    resp.child("description").value as String?,
                                    resp.child("url").value as String?,
                                    resp.key)
                    mVideoBatik.let { listData.add(it) }
                }
                recyclerView.adapter = VideobatikViewAdapter(listData)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "messages:onCancelled: ${error.message}")
            }
        }
        myRef.addValueEventListener(messagesListener)

        deleteSwipe(recyclerView)
    }

    class VideobatikViewAdapter(private val values: List<Data>) :
            RecyclerView.Adapter<VideobatikViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.video_batik_content, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val mVideoBatik = values[position]
            holder.mNameTextView.text = mVideoBatik.name
            holder.mDateTextView.text = mVideoBatik.date
            holder.mAsalTextView.text = mVideoBatik.asal + " "
            holder.mPosterImgeView.let {
                Glide.with(holder.itemView.context)
                        .load(mVideoBatik.url)
                        .into(it)
            }

            holder.itemView.setOnClickListener { v ->
                val intent = Intent(v.context, DetailActivity::class.java).apply {
                    putExtra("key", mVideoBatik.key)
                }
                v.context.startActivity(intent)
            }

            holder.itemView.setOnLongClickListener{ v ->
                val intent = Intent(v.context, EditActivity::class.java).apply {
                    putExtra("key", mVideoBatik.key)
                }
                v.context.startActivity(intent)
                true
            }

        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mNameTextView: TextView = view.findViewById(R.id.nameTextView) as TextView
            val mDateTextView: TextView = view.findViewById(R.id.dateTextView) as TextView
            val mAsalTextView: TextView = view.findViewById(R.id.asalTextView) as TextView
            val mPosterImgeView: ImageView = view.findViewById(R.id.posterImgeView) as ImageView
        }
    }

    private fun deleteSwipe(recyclerView: RecyclerView){
        val touchHelperCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val imageFirebaseStorage = FirebaseStorage.getInstance().reference.child("batik/img"+listData[viewHolder.adapterPosition].key)
                imageFirebaseStorage.delete()

                listData[viewHolder.adapterPosition].key?.let { myRef.child(it).setValue(null) }
                listData.removeAt(viewHolder.adapterPosition)

                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}