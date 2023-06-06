package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.databinding.FragmentSearchServiceBinding
import com.jualin.apps.ui.adapter.SearchServiceAdapter
import com.jualin.apps.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchServiceFragment : Fragment() {

    private var _binding: FragmentSearchServiceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString("query") ?: ""

        viewModel.searchService(query).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    setupSuccessView(it.data)
                }

                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }
    }

    private fun setupSuccessView(data: List<Service>) {
        binding.apply {
            if (data.isNotEmpty()) {
                rvNearbyService.visibility = View.VISIBLE
                pageNotFound.root.visibility = View.GONE

                rvNearbyService.layoutManager = LinearLayoutManager(requireContext())
                rvNearbyService.adapter = SearchServiceAdapter(data)
            } else {
                pageNotFound.root.visibility = View.VISIBLE
                rvNearbyService.visibility = View.GONE
            }
        }
    }
}