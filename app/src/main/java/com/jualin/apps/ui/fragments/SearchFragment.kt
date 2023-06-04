package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jualin.apps.R
import com.jualin.apps.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = SearchFragmentArgs.fromBundle(requireArguments()).query

        binding.apply {
            searchView.setQuery(query, false)
            searchView.clearFocus()

            backButton.setOnClickListener {
                findNavController().navigateUp()
            }

            viewPager.adapter = PagerAdapter(this@SearchFragment)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.produk)
                    1 -> getString(R.string.jasa)
                    else -> throw IllegalArgumentException("Invalid position: $position")
                }
            }.attach()
        }
    }

    private inner class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            val fragment = when (position) {
                0 -> SearchProductFragment()
                1 -> SearchServiceFragment()
                else -> throw IllegalStateException("Invalid position $position")
            }
            return fragment
        }
    }
}