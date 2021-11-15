package com.example.gorentapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gorentapps.databinding.ActivityLoginBinding
import com.example.gorentapps.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Buat ilangin AppBar :
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.apply {
            // Aksi ketika button regist di klik
            btnRegistration.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
            }

            // Aksi ketika button login di klik
            btnLogin.setOnClickListener {
                when {
                    // Validasi email tidak boleh kosong :
                    edtEmail.text.isEmpty() -> {
                        edtEmail.error = "Harap masukkan email"
                        return@setOnClickListener
                    }
                    // Validasi password tidak boleh kosong :
                    edtPassword.text.isEmpty() -> {
                        edtPassword.error = "Harap masukkan password"
                        return@setOnClickListener
                    }
                    // Ketika field email dan password terisi mengecek data ke db firebase :
                    else -> auth.signInWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString()).addOnCompleteListener {
                        // Ketika data yang di kirim terdaftar maka pergi ke halaman main(utama)
                        if (it.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                            Toast.makeText(this@LoginActivity, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                            // Ketika data yang di kirim tidak terdaftar dimunculkan pesan toast
                        } else {
                            Toast.makeText(this@LoginActivity, "Login gagal, harap coba lagi", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}