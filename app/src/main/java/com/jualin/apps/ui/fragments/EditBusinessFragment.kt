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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jualin.apps.R
import com.jualin.apps.data.Result
import com.jualin.apps.databinding.FragmentEditBusinessBinding
import com.jualin.apps.ui.viewmodel.UmkmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBusinessFragment : Fragment() {

    private var _binding: FragmentEditBusinessBinding? = null
    private val binding get() = _binding!!

    private lateinit var loader: AlertDialog
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null

    private val viewModel: UmkmViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                getMyLastLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getMyLastLocation()
            }

            else -> {
                // No location access granted.
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
        _binding = FragmentEditBusinessBinding.inflate(inflater, container, false)
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
        getMyLastLocation()

        val isNewBusiness = EditBusinessFragmentArgs.fromBundle(requireArguments()).isNewBusiness

        if (isNewBusiness) {
            setupViewForAddBusiness()
        } else {
            setupViewForEditBusiness()
        }

        binding.tvLocation.text = getString(R.string.memuat)
    }

    private fun setupViewForAddBusiness() {
        binding.apply {
            pageTitle.text = getString(R.string.tambah_umkm)
            btnSubmit.text = getString(R.string.buat_sekarang)

            btnSubmit.setOnClickListener {
                val nama = edNamaUsaha.text.toString()
                val kategori = edKategori.text.toString()
                val noTelp = edNoHp.text.toString()
                val deskripsi = edDeskripsi.text.toString()

                val lat = location?.latitude
                val long = location?.longitude

                viewModel.addUMKM(
                    nama,
                    kategori,
                    noTelp,
                    deskripsi,
                    lat,
                    long
                ).observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "Berhasil menambahkan UMKM",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigateUp()
                        }

                        is Result.Loading -> {}
                        is Result.Error -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupViewForEditBusiness() {
        val businessId = EditBusinessFragmentArgs.fromBundle(requireArguments()).businessId

        binding.apply {
            pageTitle.text = getString(R.string.edit_data_umkm)
            btnSubmit.text = getString(R.string.simpan)

            btnSubmit.setOnClickListener {
                val nama = edNamaUsaha.text.toString()
                val kategori = edKategori.text.toString()
                val noTelp = edNoHp.text.toString()
                val deskripsi = edDeskripsi.text.toString()

                val lat = location?.latitude
                val long = location?.longitude

                viewModel.editBusiness(
                    nama,
                    kategori,
                    noTelp,
                    deskripsi,
                    lat,
                    long
                ).observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "Berhasil edit UMKM",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigateUp()
                        }

                        is Result.Loading -> {}
                        is Result.Error -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            viewModel.getBusinessById(businessId).observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Success -> {
                        val business = it.data
                        edNamaUsaha.setText(business.name)
                        edKategori.setText(business.category)
                        edNoHp.setText(business.phone)
                        edDeskripsi.setText(business.description)
                    }

                    is Result.Loading -> {}
                    is Result.Error -> {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location: Location? ->
                    if (location!=null) {
                        this.location = location
                        binding.tvLocation.text = getString(
                            R.string.location_placeholder,
                            location.latitude.toString(),
                            location.longitude.toString()
                        )
                        setLoadingState(false)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Lokasi tidak ditemukan",
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