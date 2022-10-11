package com.example.firebasesignin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initFields()

    }

    private fun initFields() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            SignInActivity.start(this)
        }, 3000)
    }


}