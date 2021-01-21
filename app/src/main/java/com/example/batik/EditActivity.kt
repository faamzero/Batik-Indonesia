package com.example.batik

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.batik.databinding.ActivityEditBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EditActivity : AppCompatActivity() {
    private lateinit var bindingActivityEdit: ActivityEditBinding
    private val file = 1
    private var fileUri: Uri? = null
    private var imageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivityEdit = ActivityEditBinding.inflate(layoutInflater)
        val view = bindingActivityEdit.root
        setContentView(view)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("batik").child(
                key.toString()
        )

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val mData: Data? = dataSnapshot.getValue(Data::class.java)
                if (mData != null) {

                    bindingActivityEdit.nameEditText.text = Editable.Factory.getInstance().newEditable(mData.name)
                    bindingActivityEdit.dateEditText.text = Editable.Factory.getInstance().newEditable(mData.date)
                    bindingActivityEdit.asalEditText.text = Editable.Factory.getInstance().newEditable(mData.asal)
                    bindingActivityEdit.descriptionEditText.text = Editable.Factory.getInstance().newEditable(mData.description)

                    imageUrl = mData.url.toString()

                    if (fileUri == null) {
                        Glide.with(view)
                                .load(imageUrl)
                                .into(bindingActivityEdit.posterImageView)
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        bindingActivityEdit.saveButton.setOnClickListener {

            val name: String = bindingActivityEdit.nameEditText.text.toString()
            val date: String = bindingActivityEdit.dateEditText.text.toString()
            val asal: String = bindingActivityEdit.asalEditText.text.toString()
            val description: String = bindingActivityEdit.descriptionEditText.text.toString()

            val folder: StorageReference = FirebaseStorage.getInstance().reference.child("batik")
            val videoBatikReference: StorageReference = folder.child("img$key")

            if (fileUri == null) {
                val mVideoBatik = Data(name, date, asal, description, imageUrl)
                myRef.setValue(mVideoBatik)
            } else {
                videoBatikReference.putFile(fileUri!!).addOnSuccessListener {
                    videoBatikReference.downloadUrl.addOnSuccessListener { uri ->
                        val mVideoBatik = Data(name, date, asal, description, uri.toString())
                        myRef.setValue(mVideoBatik)
                    }
                }
            }

            startActivity(Intent(this, MainActivity::class.java))
        }

        bindingActivityEdit.posterImageView.setOnClickListener {
            fileUpload()
        }
    }

    private fun fileUpload() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, file)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == file) {
            if (resultCode == RESULT_OK) {
                fileUri = data!!.data
                bindingActivityEdit.posterImageView.setImageURI(fileUri)
            }
        }
    }
}