package com.jualin.apps.ui.fragments

import android.Manifest
import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jualin.apps.R
import com.jualin.apps.data.Result
import com.jualin.apps.data.remote.response.nearby.NearbyUmkmResponseItem
import com.jualin.apps.databinding.FragmentNearbyBinding
import com.jualin.apps.ui.adapter.NearbyBusinessAdapter
import com.jualin.apps.ui.viewmodel.NearbyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NearbyFragment : Fragment() {

    private var _binding: FragmentNearbyBinding? = null
    private val binding get() = _binding!!

    private lateinit var loader: AlertDialog
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null

    private val viewModel: NearbyViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                getLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getLocation()
            }

            else -> {
                Toast.makeText(
                    requireContext(),
                    "Please check your location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        )==PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNearbyBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loader = AlertDialog.Builder(requireContext()).apply {
            setView(R.layout.loader)
            setCancelable(false)
        }.create()

        setLoadingState(true)

        getLocation()
    }

    private fun setupView() {
        val lat = location?.latitude ?: -7.33193
        val lng = location?.longitude ?: 108.192

        viewModel.getNearbyUmkm(lat, lng).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                }

                is Result.Success -> {
                    val data = it.data
                    setupSuccess(data)
                }

                is Result.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Failed get location nearby",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupSuccess(data: List<NearbyUmkmResponseItem>) {
        setLoadingState(false)
        binding.apply {
            rvNearbyUmkm.visibility = View.VISIBLE
            rvNearbyUmkm.layoutManager = LinearLayoutManager(requireContext())
            rvNearbyUmkm.adapter = NearbyBusinessAdapter(data) { id ->
                findNavController().navigate(
                    NearbyFragmentDirections.actionNearbyFragmentToBusinessDetailFragment(id)
                )
            }
        }
    }

    private fun getLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it!=null) {
                    location = it
                    setupView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please check your location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            Toast.makeText(requireContext(), "Please check your location", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        if (isLoading) {
            loader.show()
            binding.parentLayout.isEnabled = false
        } else {
            loader.dismiss()
            binding.parentLayout.isEnabled = true
        }
    }
}