package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.jualin.apps.R
import com.jualin.apps.databinding.FragmentNearbyProductBinding
import com.jualin.apps.ui.adapter.NearbyProductAdapter
import com.jualin.apps.utils.FakeData

class NearbyProductFragment : Fragment() {

    private var _binding: FragmentNearbyProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNearbyProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val layoutManager = GridLayoutManager(requireContext(), 2)
            rvNearbyProduct.layoutManager = layoutManager
            rvNearbyProduct.adapter = NearbyProductAdapter(FakeData.products)

            tvNearbyProduct.text = getString(R.string.jasa_di_sekitar_s, "Ngawi")
        }
    }
}