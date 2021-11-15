package com.example.gorentapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gorentapps.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistrationBinding

    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("user")

        binding.apply {
            // Aksi ketika button LoginHere di klik
            btnLoginHere.setOnClickListener {
                finish()
            }

            // Aksi ketika button regist di klik
            btnRegistration.setOnClickListener {
                when {
                    // Validasi name tidak boleh kosong :
                    edtName.text.isEmpty() -> {
                        edtName.error = "Harap masukkan nama"
                        return@setOnClickListener
                    }
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
                    // Ketika field name, email dan password terisi maka kirim data ke db firebase :
                    else -> auth.createUserWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString()).addOnCompleteListener {
                        // Ketika data yang dikirimkan berhasil
                        if (it.isSuccessful) {
                            val currentUser = auth.currentUser
                            val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                            currentUserDb?.child("nama")?.setValue(edtName.text.toString())
                            currentUserDb?.child("email")?.setValue(edtEmail.text.toString())

                            Toast.makeText(this@RegistrationActivity, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                            finish()
                            // Ketika data yang dikirimkan tidak berhasil
                        } else {
                            Toast.makeText(this@RegistrationActivity, "Registration gagal, harap coba lagi", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}