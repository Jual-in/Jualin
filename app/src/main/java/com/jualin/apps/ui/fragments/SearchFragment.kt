package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query.isNullOrEmpty()) return false
                    findNavController().navigate(
                        SearchFragmentDirections.actionSearchFragmentSelf(
                            query
                        )
                    )
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean = true
            })

            viewPager.adapter = PagerAdapter(this@SearchFragment, query)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.produk)
                    1 -> getString(R.string.jasa)
                    else -> throw IllegalArgumentException("Invalid position: $position")
                }
            }.attach()
        }
    }

    private inner class PagerAdapter(
        fragment: Fragment,
        private val query: String
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            val bundle = Bundle().apply {
                putString("query", query)
            }
            val fragment = when (position) {
                0 -> SearchProductFragment()
                1 -> SearchServiceFragment()
                else -> throw IllegalStateException("Invalid position $position")
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}