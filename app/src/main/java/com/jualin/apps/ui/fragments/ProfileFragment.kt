package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jualin.apps.R
import com.jualin.apps.data.Result
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

        setupView()
    }

    private fun setupView() {

        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        viewModel.getUserDetail().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.tvName.text = it.data.name
                    binding.tvEmail.text = it.data.email
                    binding.tvAddress.text = it.data.alamat ?: "-"

                    if (it.data.role=="Customers") {
                        binding.btnEditBussinessProfile.visibility = View.GONE
                    } else {
                        val isNewBusiness = it.data.businessId==null
                        binding.btnEditBussinessProfile.visibility = View.VISIBLE

                        binding.btnEditBussinessProfile.text = if (isNewBusiness)
                            getString(R.string.tambah_umkm)
                        else
                            getString(R.string.edit_data_umkm)

                        binding.btnEditBussinessProfile.setOnClickListener { _ ->
                            val action =
                                ProfileFragmentDirections.actionProfileFragmentToEditBusinessFragment(
                                    it.data.businessId==null, it.data.businessId ?: 0
                                )
                            findNavController().navigate(action)
                        }
                    }
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }

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