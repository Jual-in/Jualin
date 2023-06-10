package com.jualin.apps.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.tabs.TabLayoutMediator
import com.jualin.apps.R
import com.jualin.apps.databinding.FragmentNearbyBinding
import com.jualin.apps.ui.viewmodel.NearbyViewModel
import com.jualin.apps.data.Result
import com.jualin.apps.data.remote.response.nearby.NearbyUmkmResponseItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.jualin.apps.ui.adapter.NearbyUmkmAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NearbyFragment : Fragment() {

    private var _binding: FragmentNearbyBinding? = null
    private val binding get() = _binding!!
    private  var _fusedLocationClient: FusedLocationProviderClient? = null
    private val fusedLocationClient get() = _fusedLocationClient
    private var location: Location? = null
    private val viewModel:NearbyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNearbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        getLocation()

    }
    private fun setupView(){

        val lat : Double?
        val lng : Double?

        if (location != null) {
            lat = location?.latitude
            lng = location?.longitude
        } else {
            lat = -7.33193
            lng = 108.192
        }

        viewModel.getNearbyUmkm(lat,lng).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    val data = it.data
                    setupSuccess(data)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "Failed get location nearby", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun setupSuccess(data: List<NearbyUmkmResponseItem>){
        binding.apply {
            rvNearbyUmkm.layoutManager = LinearLayoutManager(requireContext())
            rvNearbyUmkm.adapter = NearbyUmkmAdapter(data)
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
                }
            }
        }

    private fun getLocation(){
        if(ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
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
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}