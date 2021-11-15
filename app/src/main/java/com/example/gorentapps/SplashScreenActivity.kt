package com.example.gorentapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {

    private val splashScreenTimeout: Long = 2500
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = auth.currentUser
            // Ketika user masih memiliki session pergi ke halaman main(utama)
            if (currentUser != null) {
                startActivity(Intent(this@SplashScreenActivity,MainActivity::class.java))
                finish()
            // Ketika user tidak memiliki session pergi ke halaman login
            } else {
                startActivity(Intent(this@SplashScreenActivity,LoginActivity::class.java))
                finish()
            }

        },splashScreenTimeout)
    }

}