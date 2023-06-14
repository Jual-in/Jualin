package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jualin.apps.databinding.FragmentNewsViewerBinding

class NewsViewerFragment : Fragment() {

    private var _binding: FragmentNewsViewerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }

            tvNewsDate.text = "segoSambel - June 13, 2023"
            tvNewsTitle.text =
                "Kisah Inspirasi UMKM di Gianyar Berkawan Digitalisasi Berkat Fasilitasi"
            tvNewsAuthor.text = "Photo by segoSambel"

            Glide.with(requireContext())
                .load("https://images.bisnis.com/posts/2023/05/30/1660600/Namaste_21_Handmade.jpeg")
                .into(ivNewsImage)
        }
    }
}