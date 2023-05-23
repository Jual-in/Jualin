package com.jualin.apps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jualin.apps.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}