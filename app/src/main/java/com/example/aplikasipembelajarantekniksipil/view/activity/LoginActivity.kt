package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.aplikasipembelajarantekniksipil.R
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)
        etEmail = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.equals("")) {
                Toast.makeText(this, "Silahkan isi email anda", Toast.LENGTH_SHORT).show()
            } else if (password.equals("")) {
                Toast.makeText(this, "Silahkan isi password anda", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                            Log.d(">>>>>LoginActivity", "signInWithEmail:success")
                            val user = mAuth.currentUser
                            Toast.makeText(
                                this, "Login berhasil.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val navdrawIntent = Intent(this,NavdrawActivity::class.java)
                            startActivity(navdrawIntent)
                            finish()
                        } else { // If sign in fails, display a message to the user.
                            Log.w(
                                ">>>>>LoginActivity",
                                "signInWithEmail:failure",
                                task.exception
                            )
                            Toast.makeText(
                                this, "Login gagal.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        btnRegister.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser

    }
}
