package com.jualin.apps.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jualin.apps.ui.fragments.NearbyProductFragment
import com.jualin.apps.ui.fragments.NearbyServiceFragment

class NearbyPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when(position) {
            0 -> NearbyProductFragment()
            else -> NearbyServiceFragment()
        }
        return fragment
    }
}