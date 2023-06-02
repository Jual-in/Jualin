package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jualin.apps.R
import com.jualin.apps.databinding.FragmentNearbyServiceBinding
import com.jualin.apps.ui.adapter.NearbyServiceAdapter
import com.jualin.apps.utils.FakeData

class NearbyServiceFragment : Fragment() {

    private var _binding: FragmentNearbyServiceBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNearbyServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNearbyService.text = getString(R.string.jasa_di_sekitar_s, "Ngawi")
        binding.rvNearbyService.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNearbyService.adapter = NearbyServiceAdapter(FakeData.services)
    }
}