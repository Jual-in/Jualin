package com.jualin.apps.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jualin.apps.R
import com.jualin.apps.constants.NavigationConstants
import com.jualin.apps.databinding.ActivityMainBinding
import com.jualin.apps.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.host_fragment)

        validateSession()
    }

    private fun validateSession() {
        viewModel.checkLogin()
        lifecycleScope.launch {
            viewModel.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    checkCategorySelected()
                } else {
                    if (navController.currentDestination?.id==R.id.profileFragment) {
                        navController.navigate(R.id.action_profileFragment_to_loginFragment)
                    } else if (navController.currentDestination?.id==R.id.loginFragment) {
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.loginFragment, true).build()

                        navController.navigate(R.id.loginFragment, null, navOptions)
                    }
                }
            }
        }
        setupBottomNavigation()
    }

    private fun checkCategorySelected() {
        viewModel.checkCategorySelected { hasCategory ->
            if (hasCategory) {
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                navController.navigate(R.id.action_loginFragment_to_inputCategoryFragment)
            }
        }
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