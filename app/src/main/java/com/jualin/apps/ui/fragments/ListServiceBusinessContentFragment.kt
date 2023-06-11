package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jualin.apps.data.Result
import com.jualin.apps.databinding.FragmentListServiceBusinessContentBinding
import com.jualin.apps.ui.adapter.BusinessContentServiceAdapter
import com.jualin.apps.ui.viewmodel.BusinessContentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListServiceBusinessContentFragment : Fragment() {

    private var _binding: FragmentListServiceBusinessContentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusinessContentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListServiceBusinessContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BusinessContentServiceAdapter(
            emptyList(),
            object : BusinessContentServiceAdapter.OnBusinessClickListener {
                override fun onBusinessServiceRemoveClick(businessId: Int) {
                    //
                }

                override fun onBusinessServiceAddClick() {
                    //
                }
            },
        )

        binding.rvService.layoutManager = LinearLayoutManager(requireContext())
        binding.rvService.adapter = adapter

        viewModel.services.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    adapter.updateItems(it.data)
                }

                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }

        val businessId = arguments?.getInt("businessId") ?: 0
        viewModel.getServicesByBusinessId(businessId)
    }
}