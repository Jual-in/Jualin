package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jualin.apps.R
import com.jualin.apps.databinding.FragmentProfileBinding
import com.jualin.apps.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout()

    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            val alertLogout = AlertDialog.Builder(requireContext())
            alertLogout.setIcon(R.drawable.product_logo)
            alertLogout.setTitle("LogOut")
            alertLogout.setMessage("Are you sure want to logout?")
            alertLogout.setPositiveButton("Yes") { _, _ ->
                viewModel.logout()
                Toast.makeText(requireContext(), "Logout Success", Toast.LENGTH_SHORT).show()
            }
            alertLogout.setNegativeButton("No") { _, _ ->
                Toast.makeText(requireContext(), "Logout Cancel", Toast.LENGTH_SHORT).show()
            }
            alertLogout.show()
        }
    }

}