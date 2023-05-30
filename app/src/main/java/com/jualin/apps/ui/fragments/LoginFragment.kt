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
import com.jualin.apps.data.local.entity.User
import com.jualin.apps.data.local.preferences.UserPreferences
import com.jualin.apps.databinding.FragmentLoginBinding
import com.jualin.apps.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()
    private val userModel: User = User()
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreferences = UserPreferences(requireContext())

        login()

        setupAction()
    }
    private fun login(){
        binding.btnLogin.setOnClickListener{
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginViewModel.login(
                email,
                password
            ).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        val response = result.data
                        saveToken(response.token)
                        Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        Log.d("LoginFragment", "Success")
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Login Gagal", Toast.LENGTH_SHORT).show()
                        Log.d("LoginFragment", "Error")
                    }
                    is Result.Loading -> {
                        Log.d("LoginFragment", "Loading")
                    }
                }
            }
        }
    }

    private fun saveToken(token: String) {
        userModel.token = token
        userPreferences.setUser(userModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leak
    }

    private fun setupAction() {
        binding.linkToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}