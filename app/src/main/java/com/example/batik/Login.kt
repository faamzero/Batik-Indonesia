package com.example.batik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    lateinit var btn_daftar: TextView
    lateinit var btn_login: Button
    lateinit var auth: FirebaseAuth
    lateinit var email: EditText
    lateinit var pass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        email  = findViewById(R.id.email_log)
        pass = findViewById(R.id.password_log)
        btn_login = findViewById(R.id.btn_login)

        btn_daftar = findViewById(R.id.tv_daftar)
        btn_daftar.setOnClickListener{
            startActivity(Intent(this, Register::class.java))
        }
        btn_login.setOnClickListener {
            fun_masuk()
        }
    }

    fun fun_masuk(){
        if (email.text.toString().isNotBlank() && pass.text.toString().isNotBlank() ){
            auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java))
                    }else{
                        Toast.makeText(this, "Kesalahan saat login", Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this, "email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
    }
}