package com.jualin.apps.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.jualin.apps.MainActivity
import com.jualin.apps.R
import com.jualin.apps.data.local.entity.User
import com.jualin.apps.data.local.preferences.UserPreferences
import com.jualin.apps.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPreferences: UserPreferences
    private var userModel: User = User()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())
        userModel = userPreferences.getUser()

        logout()

    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            val alertLogout = AlertDialog.Builder(requireContext())
            alertLogout.setIcon(R.drawable.product_logo)
            alertLogout.setTitle("LogOut")
            alertLogout.setMessage("Are you sure want to logout?")
            alertLogout.setPositiveButton("Yes") { _, _ ->
                userModel.token = ""
                userPreferences.setUser(userModel)
                Toast.makeText(requireContext(), "Logout Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
            alertLogout.setNegativeButton("No") { _, _ ->
                Toast.makeText(requireContext(), "Logout Cancel", Toast.LENGTH_SHORT).show()
            }
            alertLogout.show()
        }
    }

}