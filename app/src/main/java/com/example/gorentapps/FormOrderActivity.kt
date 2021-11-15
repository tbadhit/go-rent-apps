package com.example.gorentapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gorentapps.databinding.ActivityFormOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.gorentapps.model.Car
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener




class FormOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormOrderBinding
    lateinit var auth: FirebaseAuth

    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("orders")

        val car = intent.getParcelableExtra<Car>(EXTRA_CAR) as Car

        binding.apply {
            btnOrder.setOnClickListener {
                when {
                    // Validasi form name tidak boleh kosong :
                    edtName.text.isEmpty() -> {
                        edtPhone.error = "Harap masukkan nama pemesan"
                        return@setOnClickListener
                    }
                    // Validasi form phone tidak boleh kosong :
                    edtPhone.text.isEmpty() -> {
                        edtPhone.error = "Harap masukkan nomor telpon"
                        return@setOnClickListener
                    }
                    // Validasi form alamat tidak boleh kosong :
                    edtAddress.text.isEmpty() -> {
                        edtAddress.error = "Harap masukkan alamat"
                        return@setOnClickListener
                    }
                    // Validasi form dari tanggal tidak boleh kosong :
                    edtFromDate.text.isEmpty() -> {
                        edtFromDate.error = "Harap masukkan tanggal pesanan"
                        return@setOnClickListener
                    }
                    // Validasi form sampai tanggal tidak boleh kosong :
                    edtUntilDate.text.isEmpty() -> {
                        edtUntilDate.error = "Harap masukkan tanggal pesanan"
                        return@setOnClickListener
                    }
                    // Ketika field name, email dan password terisi maka kirim data ke db firebase :
                    else -> {
                        val currentUser = auth.currentUser
                        val currentUserDb = databaseReference?.child((currentUser?.uid!!))

                        currentUserDb?.child("nama_pemesan")?.setValue(edtName.text.toString())
                        currentUserDb?.child("mobil_yang_dipesan")?.setValue(car.name.toString())
                        currentUserDb?.child("penumpang")?.setValue(car.passenger.toString())
                        currentUserDb?.child("harga_sewa")?.setValue(car.rentalPrice.toString())
                        currentUserDb?.child("nomor_telfon")?.setValue(edtPhone.text.toString())
                        currentUserDb?.child("alamat")?.setValue(edtAddress.text.toString())
                        currentUserDb?.child("dari_tanggal")?.setValue(edtFromDate.text.toString())
                        currentUserDb?.child("sampai_tanggal")?.setValue(edtUntilDate.text.toString())
                        Toast.makeText(this@FormOrderActivity, "Order Berhasil", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_CAR = "extra_car"
    }
}