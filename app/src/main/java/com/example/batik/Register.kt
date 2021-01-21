package com.example.batik

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var btn_reg: Button
    lateinit var email: EditText
    lateinit var pass: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.email_reg)
        pass = findViewById(R.id.password_reg)
        btn_reg = findViewById(R.id.btn_register)
        btn_reg.setOnClickListener {
            func_register()
        }
    }

    fun func_register() {
        if (email.text.toString().isEmpty()) {
            email.error = "harap isi email dengan benar!"
            email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = " Format email tidak valid"
            email.requestFocus()
            return
        }
        if (pass.text.toString().isEmpty()) {
            pass.error = "Password yang anda masukan kurang!"
            pass.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(this, "error register", Toast.LENGTH_SHORT).show()

                    } else {
                        startActivity(Intent(this, Register::class.java))
                        finish()
                    }
                }
    }
}