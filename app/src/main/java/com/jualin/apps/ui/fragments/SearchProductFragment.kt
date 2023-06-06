package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Product
import com.jualin.apps.databinding.FragmentSearchProductBinding
import com.jualin.apps.ui.adapter.SearchProductAdapter
import com.jualin.apps.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString("query") ?: ""

        viewModel.searchProduct(query).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    setupSuccessView(it.data)
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }

    private fun setupSuccessView(data: List<Product>) {
        binding.apply {
            if (data.isNotEmpty()) {
                rvNearbyProduct.visibility = View.VISIBLE
                pageNotFound.root.visibility = View.GONE

                val layoutManager = GridLayoutManager(requireContext(), 2)
                rvNearbyProduct.layoutManager = layoutManager
                rvNearbyProduct.adapter = SearchProductAdapter(data)
            } else {
                pageNotFound.root.visibility = View.VISIBLE
                rvNearbyProduct.visibility = View.GONE
            }
        }
    }
}