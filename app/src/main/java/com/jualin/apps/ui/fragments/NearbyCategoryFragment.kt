package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.jualin.apps.R
import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.databinding.FragmentNearbyCategoryBinding
import com.jualin.apps.ui.adapter.NearbyBusinessAdapter
import com.jualin.apps.utils.FakeData

class NearbyCategoryFragment : Fragment() {

    private var _binding: FragmentNearbyCategoryBinding? = null
    private val binding get() = _binding!!

    private val businessList = mutableListOf<Business>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNearbyCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.getString(ARG_CATEGORY) ?: ""

        if (category==NearbyCategory.PRODUCT.value) {
            binding.tvListTitle.text = getString(R.string.produk_di_sekitar_s)

            businessList.addAll(FakeData.business)

        } else {
            binding.tvListTitle.text = getString(R.string.jasa_di_sekitar_s)

            businessList.addAll(FakeData.business)
        }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvNearbyCategory.layoutManager = layoutManager
        binding.rvNearbyCategory.adapter = NearbyBusinessAdapter(businessList,
            object : NearbyBusinessAdapter.OnBusinessClickListener {
                override fun onBusinessClick(businessId: Int) {
                    //
                }

            })

    }

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: NearbyCategory): NearbyCategoryFragment {
            val fragment = NearbyCategoryFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category.value)
            fragment.arguments = args
            return fragment
        }


    }
}

enum class NearbyCategory(val value: String) {
    PRODUCT("produk"),
    SERVICE("jasa")
}
