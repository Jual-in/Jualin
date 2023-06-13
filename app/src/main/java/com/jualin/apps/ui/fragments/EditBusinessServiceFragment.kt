package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.databinding.FragmentEditBusinessServiceBinding
import com.jualin.apps.ui.viewmodel.UmkmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBusinessServiceFragment : Fragment() {

    private var _binding: FragmentEditBusinessServiceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UmkmViewModel by viewModels()

    private var businessId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBusinessServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isNewService =
            EditBusinessServiceFragmentArgs.fromBundle(requireArguments()).isNewService

        if (isNewService) {
            businessId = EditBusinessServiceFragmentArgs.fromBundle(requireArguments()).businessId
            setupForAddService()
        } else {
            val service = EditBusinessServiceFragmentArgs.fromBundle(requireArguments()).service
            if (service!=null) {
                setupForEditService(service)
            }
        }
    }

    private fun setupForEditService(service: Service) {
        binding.pageTitle.text = "Edit Jasa"
        binding.btnSubmit.text = "Ubah Jasa"

        binding.edNamaJasa.setText(service.name)
        binding.edPrice.setText(service.price.toString())
        binding.edDiscount.setText(service.discount.toString())

        binding.btnSubmit.setOnClickListener {
            val name = binding.edNamaJasa.text.toString()
            val price = binding.edPrice.text.toString().toInt()
            val discount = binding.edDiscount.text.toString().toInt()

            viewModel.editService(service.id, name, price, discount).observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Berhasil Mengubah Jasa",
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

    private fun setupForAddService() {
        binding.btnSubmit.setOnClickListener {
            val name = binding.edNamaJasa.text.toString()
            val price = binding.edPrice.text.toString().toInt()
            val discount = binding.edDiscount.text.toString().toInt()

            viewModel.addService(businessId, name, price, discount).observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Berhasil Menambah Jasa",
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