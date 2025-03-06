package com.example.chapter8

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chapter8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.loadImageButton.setOnClickListener {
            checkPermission()
        }
    }

    private fun checkPermission() {

//        when {
//            ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED -> {
//
//            }
//        }

        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_READ_EXTERNAL_STORAGE
        )
    }

    private fun loadImage() {

    }

    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }
}