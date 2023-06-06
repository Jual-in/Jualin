package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jualin.apps.R
import com.jualin.apps.data.Result
import com.jualin.apps.databinding.FragmentRegisterBinding
import com.jualin.apps.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAction() {
        binding.linkToLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            val seller = "Sellers"
            val customer = "Customers"
            val name = binding.editTextNama.text.toString()
            val email = binding.editTextEmail.text.toString().trim()
            val passwordEntry = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextPasswordConfirm.text.toString().trim()
            val isChecked = binding.switchUser.isChecked
            val role = if (isChecked) {
                seller
            } else {
                customer
            }


            if (passwordEntry!=confirmPassword) {
                Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordEntry.length < 8) {
                Toast.makeText(requireContext(), "Password minimal 8 karakter", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (name.isEmpty() || email.isEmpty() || passwordEntry.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            viewModel.register(name, email, passwordEntry, role)
                .observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Success -> {
                            Log.d("RegisterFragment", "Success")
                            Toast.makeText(requireContext(), "Register Success", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                        is Result.Error -> {
                            Log.d("RegisterFragment", "Error")
                            Toast.makeText(requireContext(), "Register Gagal", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is Result.Loading -> {
                            Log.d("RegisterFragment", "Loading")

                        }
                    }
                }
        }
    }
}