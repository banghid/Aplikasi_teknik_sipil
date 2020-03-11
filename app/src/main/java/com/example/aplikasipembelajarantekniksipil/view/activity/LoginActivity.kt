package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasipembelajarantekniksipil.R
import com.google.firebase.auth.FirebaseAuth
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal


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

        showPrompt()

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
                            val prefManager = PreferenceManager.getDefaultSharedPreferences(this)

                            if (!prefManager.getBoolean("MASTER_PROMPT", false)){
                                val introIntent = Intent(this, MenuIntro::class.java)
                                startActivity(introIntent)
                                val prefEditor = prefManager.edit()
                                prefEditor.putBoolean("MASTER_PROMPT", true)
                                prefEditor.apply()
                                finish()
                            }else{
                                val intentNavdraw = Intent(this,NavdrawActivity::class.java)
                                startActivity(intentNavdraw)
                                finish()
                            }

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

    private fun showPrompt() {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)

        if (!prefManager.getBoolean("LOGIN_TUTORIAL", false)) {
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.rl_login_form)
                .setPrimaryText("Form Login")
                .setSecondaryText("Isi email dan password apabila sudah memiliki akun")
                .setBackButtonDismissEnabled(true)
                .setPromptStateChangeListener { prompt, state ->
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                    ) {
                        val prefEditor = prefManager.edit()
                        prefEditor.putBoolean("LOGIN_TUTORIAL", true)
                        prefEditor.apply()
                        showChapterPrompt()
                    }
                }
                .setPromptBackground(RectanglePromptBackground())
                .setPromptFocal(RectanglePromptFocal())
                .show()
        }
    }

    private fun showChapterPrompt() {
        MaterialTapTargetPrompt.Builder(this)
            .setTarget(R.id.btn_login)
            .setPrimaryText("Tombol Masuk")
            .setSecondaryText("Klik tombol masuk untuk melakukan proses login!")
            .setBackButtonDismissEnabled(true)
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                    state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                ) {
                    showRegisterButton()
                }
            }
            .setPromptBackground(RectanglePromptBackground())
            .setPromptFocal(RectanglePromptFocal())
            .show()
    }

    private fun showRegisterButton(){
        MaterialTapTargetPrompt.Builder(this)
            .setTarget(R.id.btn_register)
            .setPrimaryText("Tombol Daftar")
            .setSecondaryText("Klik tombol daftar apabila belum memiliki akun")
            .setBackButtonDismissEnabled(true)
            .setPromptBackground(RectanglePromptBackground())
            .setPromptFocal(RectanglePromptFocal())
            .show()
    }
}
