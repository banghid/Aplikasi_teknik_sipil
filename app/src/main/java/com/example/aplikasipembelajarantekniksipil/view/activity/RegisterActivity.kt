package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasipembelajarantekniksipil.R
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnRegister: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister = findViewById(R.id.btn_register_process)
        etEmail = findViewById(R.id.et_register_username)
        etPassword = findViewById(R.id.et_register_password)
        mAuth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.equals("")) {
                Toast.makeText(this, "Silahkan isi email anda", Toast.LENGTH_SHORT).show()
            } else if (password.equals("")) {
                Toast.makeText(this, "Silahkan isi password anda", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                            val user = mAuth.currentUser
                            Toast.makeText(
                                this, "Registrasi Berhasil.",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else { // If sign in fails, display a message to the user.
                            Log.d("RegisterActivity", task.exception?.message.toString())
                            Toast.makeText(
                                this, "Registrasi gagal.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
            }


        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }
}
