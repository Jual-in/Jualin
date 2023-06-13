package com.jualin.apps.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jualin.apps.data.Result
import com.jualin.apps.databinding.FragmentEditBusinessProductBinding
import com.jualin.apps.ui.viewmodel.UmkmViewModel
import com.jualin.apps.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class EditBusinessProductFragment : Fragment() {

    private var _binding: FragmentEditBusinessProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UmkmViewModel by viewModels()

    private var photo: File? = null
    private var businessId: Int = 0
    private var productId: Int = 0

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode==Activity.RESULT_OK) {
            val selectedImage = it.data?.data as Uri
            selectedImage.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                photo = myFile
                updateImage()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBusinessProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isNewProduct =
            EditBusinessProductFragmentArgs.fromBundle(requireArguments()).isNewProduct

        if (isNewProduct) {
            businessId = EditBusinessProductFragmentArgs.fromBundle(requireArguments()).businessId
            setupForAddProduct()
        } else {
            productId = EditBusinessProductFragmentArgs.fromBundle(requireArguments()).productId
            setupForEditProduct()
        }

        binding.ivImageBarang.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "Choose a Picture")
            launcherIntentGallery.launch(chooser)
        }
    }

    private fun setupForEditProduct() {
        binding.pageTitle.text = "Edit Produk"
        binding.btnSubmit.text = "Ubah Produk"

        viewModel.getProductById(productId).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    val product = it.data
                    binding.edNamaBarang.setText(product.name)
                    binding.edHargaBarang.setText(product.price.toString())
                    binding.edDiskon.setText(product.discount.toString())
                    Glide.with(requireContext())
                        .load(product.photoUrl)
                        .into(binding.ivImageBarang)
                }

                else -> {}
            }
        }

        binding.btnSubmit.setOnClickListener {
            val name = binding.edNamaBarang.text.toString()
            val price = binding.edHargaBarang.text.toString().toInt()
            val discount = binding.edDiskon.text.toString().toInt()

            viewModel.editProduct(productId, name, price, discount, photo)
                .observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "Berhasil Mengubah Produk",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigateUp()
                        }

                        is Result.Loading -> {}
                        is Result.Error -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
        }
    }

    private fun setupForAddProduct() {
        binding.btnSubmit.setOnClickListener {
            if (photo==null) {
                Toast.makeText(requireContext(), "Pilih Gambar terlebih dahulu", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val name = binding.edNamaBarang.text.toString()
                val price = binding.edHargaBarang.text.toString().toInt()
                val discount = binding.edDiskon.text.toString().toInt()

                viewModel.addProduct(businessId, name, price, discount, photo!!)
                    .observe(viewLifecycleOwner) {
                        when (it) {
                            is Result.Success -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Berhasil Menambah Produk",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().navigateUp()
                            }

                            is Result.Loading -> {}
                            is Result.Error -> {
                                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
            }
        }
    }

    private fun updateImage() {
        if (photo!=null) {
            Glide.with(requireContext())
                .load(photo)
                .into(binding.ivImageBarang)
        }
    }
}