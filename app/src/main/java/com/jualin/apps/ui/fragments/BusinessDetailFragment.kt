package com.jualin.apps.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jualin.apps.R
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.databinding.FragmentBusinessDetailBinding
import com.jualin.apps.ui.adapter.BusinessDetailProductAdapter
import com.jualin.apps.ui.adapter.BusinessDetailServiceAdapter
import com.jualin.apps.ui.viewmodel.BusinessDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessDetailFragment : Fragment() {

    private var _binding: FragmentBusinessDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusinessDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val businessId = BusinessDetailFragmentArgs.fromBundle(requireArguments()).businessId

        viewModel.getBusinessById(businessId).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    setupView(it.data)
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }

    private fun setupView(data: Business) {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }

            tvBusinessName.text = data.name
            tvDescription.text = data.description

            Glide.with(requireContext())
                .load(R.drawable.default_business)
                .into(ivBusinessImage)

            btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${data.phone}"))
                startActivity(intent)
            }
        }

        fetchProductsAndServices(data.id)
    }

    private fun fetchProductsAndServices(businessId: Int) {
        viewModel.getProductsByBusinessId(businessId).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (it.data.isNotEmpty()) {
                        binding.tvProductTitle.visibility = View.VISIBLE
                        binding.rvProductList.visibility = View.VISIBLE

                        binding.rvProductList.layoutManager = object : GridLayoutManager(requireContext(), 2) {
                            override fun canScrollVertically(): Boolean = false
                        }
                        binding.rvProductList.adapter = BusinessDetailProductAdapter(it.data)
                    }
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }

        viewModel.getServicesByBusinessId(businessId).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (it.data.isNotEmpty()) {
                        binding.tvServiceTitle.visibility = View.VISIBLE
                        binding.rvServiceList.visibility = View.VISIBLE

                        binding.rvServiceList.layoutManager = object : LinearLayoutManager(requireContext()) {
                            override fun canScrollVertically(): Boolean = false
                        }
                        binding.rvServiceList.adapter = BusinessDetailServiceAdapter(it.data)
                    }
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }
}