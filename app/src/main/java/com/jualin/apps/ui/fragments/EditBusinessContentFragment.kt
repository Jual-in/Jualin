package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jualin.apps.R
import com.jualin.apps.databinding.FragmentEditBusinessContentBinding

class EditBusinessContentFragment : Fragment() {

    private var _binding: FragmentEditBusinessContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBusinessContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val businessId = EditBusinessContentFragmentArgs.fromBundle(requireArguments()).businessId

        binding.viewPager.adapter = PagerAdapter(this, businessId)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.produk)
                1 -> getString(R.string.jasa)
                else -> throw IllegalStateException("Invalid position $position")
            }
        }.attach()
    }

    private inner class PagerAdapter(
        fragment: Fragment,
        private val businessId: Int
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            val bundle = Bundle().apply {
                putInt("businessId", businessId)
            }
            val fragment = when (position) {
                0 -> ListProductBusinessContentFragment()
                1 -> ListServiceBusinessContentFragment()
                else -> throw IllegalStateException("Invalid position $position")
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}