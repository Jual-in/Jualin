package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jualin.apps.data.Result
import com.jualin.apps.databinding.FragmentListProductBusinessContentBinding
import com.jualin.apps.ui.adapter.BusinessContentProductAdapter
import com.jualin.apps.ui.viewmodel.BusinessContentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductBusinessContentFragment : Fragment() {

    private var _binding: FragmentListProductBusinessContentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusinessContentViewModel by viewModels()

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

        val adapter = BusinessContentProductAdapter(
            emptyList(),
            object : BusinessContentProductAdapter.OnBusinessClickListener {
                override fun onBusinessProductRemoveClick(businessId: Int) {
                    //
                }

                override fun onBusinessProductAddClick() {
                    //
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

        val businessId = arguments?.getInt("businessId") ?: 0
        viewModel.getProductsByBusinessId(businessId)
    }
}