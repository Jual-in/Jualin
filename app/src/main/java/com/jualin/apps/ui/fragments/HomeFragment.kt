package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jualin.apps.R
import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.databinding.FragmentHomeBinding
import com.jualin.apps.ui.adapter.RecommendedBusinessAdapter
import com.jualin.apps.ui.viewmodel.HomeViewModel
import com.jualin.apps.utils.FakeData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()


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
                    override fun onBusinessClick(business: Business) {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToBusinessDetailFragment(
                                business = business
                            )
                        findNavController().navigate(action)
                    }
                },
            )

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query!=null) {
                        performSearch(query)
                        searchView.setQuery("", false)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean = true
            })
        }
    }

    private fun performSearch(query: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(query)
        findNavController().navigate(action)
    }
}