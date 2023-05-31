package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jualin.apps.R
import com.jualin.apps.data.Result
import com.jualin.apps.databinding.FragmentLoginBinding
import com.jualin.apps.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leak
    }

    private fun setupAction() {
        binding.linkToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            viewModel.login(
                email,
                password
            ).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Login Gagal", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Loading -> {
                        Log.d("LoginFragment", "Loading")
                    }
                }
            }
        }
    }
}