package com.jualin.apps.ui.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jualin.apps.R
import com.jualin.apps.data.Result
import com.jualin.apps.databinding.FragmentEditProfileBinding
import com.jualin.apps.ui.viewmodel.AuthViewModel
import com.jualin.apps.utils.rotateFile
import com.jualin.apps.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        uploadImage()

    }

    private fun setupView() {
        viewModel.getUserDetail().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.editTextNama.setText(it.data.name)
                    binding.editTextEmail.setText(it.data.email)
                    binding.editTextAlamat.setText(it.data.alamat ?: "-")
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }

        binding.btnEditProfile.setOnClickListener {
            val name = binding.editTextNama.text.toString()
            val email = binding.editTextEmail.text.toString()
            val passwordEntry = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextPasswordConfirm.text.toString().trim()
            val alamat = binding.editTextAlamat.text.toString()

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

            viewModel.updateUser(name, email, passwordEntry, alamat).observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Berhasil edit Profile",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
                    }

                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Gagal edit Profile", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is Result.Loading -> {}
                }
            }
        }
    }

    private fun uploadImage() {
        binding.civProfile.setOnClickListener {
            startGallery()
        }
        binding.btnEditPhoto.setOnClickListener {
            if (getFile!=null) {
                viewModel.uploadPhotoUser(getFile!!).observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "Berhasil upload gambar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Result.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "Gagal upload gambar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Result.Loading -> {}
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode==AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            myFile.let { file ->
                rotateFile(file, false)
                getFile = file
                binding.civProfile.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }

            getFile = myFile

            binding.civProfile.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}