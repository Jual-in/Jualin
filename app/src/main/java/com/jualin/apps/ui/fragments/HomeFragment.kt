package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jualin.apps.R
import com.jualin.apps.databinding.FragmentHomeBinding
import com.jualin.apps.ui.adapter.RecommendedBusinessAdapter
import com.jualin.apps.utils.FakeData

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.apply {
            include1.tvDiscount.text = getString(R.string.discount_up_to, 50)
            include2.tvDiscount.text = getString(R.string.discount_up_to, 40)
            include3.tvDiscount.text = getString(R.string.discount_up_to, 30)
            include4.tvDiscount.text = getString(R.string.discount_up_to, 20)

            Glide.with(requireContext())
                .load("https://picsum.photos/300/180")
                .into(carouselView)

            rvRecommended.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            rvRecommended.adapter = RecommendedBusinessAdapter(
                FakeData.business,
                object : RecommendedBusinessAdapter.OnBusinessClickListener {
                    override fun onBusinessClick(businessId: Int) {
                        val action = HomeFragmentDirections.actionHomeFragmentToBusinessDetailFragment(businessId)
                        findNavController().navigate(action)
                    }
                },
            )
        }
    }
}