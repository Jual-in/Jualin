package com.jualin.apps

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.jualin.apps.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jualin.apps.constants.NavigationConstants
import com.jualin.apps.data.local.entity.User
import com.jualin.apps.data.local.preferences.UserPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var userModel: User
    private lateinit var userPreferences: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        Handler(Looper.getMainLooper()).postDelayed({
            if (userModel.token == "") {
                navController = findNavController(R.id.host_fragment)
                binding.bottomNavigationView.visibility = View.GONE
                setupBottomNavigation()
            } else {
                navController = Navigation.findNavController(this, R.id.host_fragment).apply {
                    navigate(R.id.action_loginFragment_to_homeFragment)
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                setupBottomNavigation()
            }
        }, 1000)

    }

    private fun setupBottomNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (NavigationConstants.DESTINATIONS_WITHOUT_BOTTOM_NAVIGATION.contains(destination.id)) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}