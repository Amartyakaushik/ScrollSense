package com.example.scrollsense.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.scrollsense.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//SplashScreen activity that displays an introductory screen for a brief period before launching the main activity.
@SuppressLint("CustomSplashScreen") // Suppresses the warning for not using the new SplashScreen API
class SplashScreen : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen) // Sets the splash screen layout

        // Use a lifecycle-aware coroutine to handle the delay
        lifecycleScope.launch {
            delay(1700) // Pause for 1700ms (1.7 seconds)

            // Create an intent to launch the MainActivity
            val startIntent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(startIntent)
            finish() // Close the SplashScreen activity to prevent users from returning to it
        }
    }
}
