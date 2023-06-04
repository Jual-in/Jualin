package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jualin.apps.databinding.FragmentSearchServiceBinding
import com.jualin.apps.ui.adapter.SearchServiceAdapter
import com.jualin.apps.utils.FakeData

class SearchServiceFragment : Fragment() {

    private var _binding: FragmentSearchServiceBinding? = null
    private val binding get() = _binding!!


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

        binding.rvNearbyService.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNearbyService.adapter = SearchServiceAdapter(FakeData.services)
    }
}