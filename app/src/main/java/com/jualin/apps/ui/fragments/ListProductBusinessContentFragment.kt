package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jualin.apps.R
import com.jualin.apps.data.Result
import com.jualin.apps.databinding.FragmentListProductBusinessContentBinding
import com.jualin.apps.ui.adapter.BusinessContentProductAdapter
import com.jualin.apps.ui.viewmodel.UmkmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductBusinessContentFragment : Fragment() {

    private var _binding: FragmentListProductBusinessContentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UmkmViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListProductBusinessContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val businessId = arguments?.getInt("businessId") ?: 0

        val adapter = BusinessContentProductAdapter(
            emptyList(),
            object : BusinessContentProductAdapter.OnBusinessClickListener {
                override fun onBusinessProductRemoveClick(productId: Int) {
                    val alertLogout = AlertDialog.Builder(requireContext())
                    alertLogout.setIcon(R.drawable.product_logo)
                    alertLogout.setTitle("Hapus Produk")
                    alertLogout.setMessage("Anda yakin untuk menghapus produk ini?")
                    alertLogout.setPositiveButton("Ya") { _, _ ->
                        viewModel.deleteProductById(productId) {
                            if (it) {
                                viewModel.getProductsByBusinessId(businessId)
                                Toast.makeText(requireContext(), "Produk Dihapus", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Gagal Menghapus Jasa",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    alertLogout.setNegativeButton("Tidak") { _, _ -> }
                    alertLogout.show()
                }

                override fun onBusinessProductAddClick() {
                    val action =
                        EditBusinessContentFragmentDirections.actionEditBusinessContentFragmentToEditBusinessProductFragment(
                            isNewProduct = true,
                            businessId = businessId
                        )
                    findNavController().navigate(action)
                }

                override fun onBusinessProductEditClick(productId: Int) {
                    val action =
                        EditBusinessContentFragmentDirections.actionEditBusinessContentFragmentToEditBusinessProductFragment(
                            isNewProduct = false,
                            productId = productId
                        )
                    findNavController().navigate(action)
                }
            },
        )

        binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProduct.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    adapter.updateItems(it.data)
                }

                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }

        viewModel.getProductsByBusinessId(businessId)
    }
}