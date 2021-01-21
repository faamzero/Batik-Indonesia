package com.example.batik

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.batik.databinding.ActivityAddBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddActivity : AppCompatActivity() {
    private lateinit var bindingActivityAdd: ActivityAddBinding
    private val database = Firebase.database
    private val myRef = database.getReference("batik")
    private val file = 1
    private var fileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivityAdd = ActivityAddBinding.inflate(layoutInflater)
        val view = bindingActivityAdd.root
        setContentView(view)

        bindingActivityAdd.saveButton.setOnClickListener {

            val name: String = bindingActivityAdd.nameEditText.text.toString()
            val date: String = bindingActivityAdd.dateEditText.text.toString()
            val asal: String = bindingActivityAdd.asalEditText.text.toString()
            val description: String = bindingActivityAdd.descriptionEditText.text.toString()
            val key: String = myRef.push().key.toString()
            val folder: StorageReference = FirebaseStorage.getInstance().reference.child("batik")
            val videoBatikReference: StorageReference = folder.child("img$key")

            if (fileUri == null) {
                val mVideoBatik = Data(name, date, asal, description)
                myRef.child(key).setValue(mVideoBatik)
            } else {
                videoBatikReference.putFile(fileUri!!).addOnSuccessListener {
                    videoBatikReference.downloadUrl.addOnSuccessListener { uri ->
                        val mVideoBatik = Data(name, date, asal, description, uri.toString())
                        myRef.child(key).setValue(mVideoBatik)
                    }
                }
            }

            finish()
        }

        bindingActivityAdd.posterImageView.setOnClickListener {
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
                bindingActivityAdd.posterImageView.setImageURI(fileUri)
            }
        }
    }
}