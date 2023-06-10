package com.jualin.apps.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.jualin.apps.R
import com.jualin.apps.databinding.FragmentUmkmBinding
import com.jualin.apps.ui.viewmodel.UmkmViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.jualin.apps.data.Result

@AndroidEntryPoint
class AddUmkmFragment : Fragment(){
    private var _binding: FragmentUmkmBinding? = null
    private  var _fusedLocationClient: FusedLocationProviderClient? = null
    private val binding get() = _binding!!
    private var location: Location ? = null
    private val viewModel: UmkmViewModel by viewModels()
    private val fusedLocationClient get() = _fusedLocationClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUmkmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

    }




    private fun setupView(){
        binding.switchUser.setOnCheckedChangeListener {_, isChecked ->
            if(isChecked) {
                getLocation()
            } else {
                location = null
            }
        }

        binding.btnAddUMKM.setOnClickListener {
            val nama = binding.edNamaUsaha.text.toString()
            val kategori = binding.edKategori.text.toString()
            val noTelp = binding.edNoHp.text.toString()
            val deskripsi = binding.edDeskripsi.text.toString()

            val lat : Double?
            val lng : Double?

            if (location != null) {
                lat = location?.latitude
                lng = location?.longitude
            } else {
                lat = 0.0
                lng = 0.0
            }

            viewModel.addUMKM(nama, kategori, noTelp, deskripsi, lat, lng).observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(requireContext(), "Berhasil menambahkan UMKM", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_TambahUmkm_to_profileFragment)
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Gagal menambahkan UMKM", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> {}
                }
            }
        }

    }

    private val requestPermissionMaps =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getLocation()
                }
                else -> {
                    Toast.makeText(requireContext(), "Please check your location", Toast.LENGTH_SHORT).show()
                    binding.switchUser.isChecked = false
                }
            }
        }

    private fun getLocation(){
        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient?.lastLocation?.addOnSuccessListener {
                if(it != null) {
                    location = it
                } else {
                    Toast.makeText(requireContext(), "Please check your location", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            requestPermissionMaps.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            Toast.makeText(requireContext(), "Please check your location", Toast.LENGTH_SHORT).show()
            binding.switchUser.isChecked = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}