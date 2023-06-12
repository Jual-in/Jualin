package com.jualin.apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jualin.apps.R
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Category
import com.jualin.apps.databinding.FragmentInputCategoryBinding
import com.jualin.apps.ui.viewmodel.InputCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputCategoryFragment : Fragment() {

    private var _binding: FragmentInputCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InputCategoryViewModel by viewModels()

    private val listCategoryId = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllCategories().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    setupView(result.data)
                }

                else -> {}
            }
        }
    }

    private fun setupView(data: List<Category>) {
        binding.apply {
            data.forEach {
                val checkbox = CheckBox(requireContext())
                checkbox.text = it.name
                checkbox.id = it.id
                checkboxContainer.addView(checkbox)

                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        listCategoryId.add(it.id)
                    } else {
                        listCategoryId.remove(it.id)
                    }
                }
            }
            btnSubmit.setOnClickListener {
                if (listCategoryId.size < 3) {
                    Toast.makeText(requireContext(), "Pilih minimal 3 kategori", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    viewModel.selectCategories(listCategoryId)
                        .observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Success -> {
                                    Toast.makeText(requireContext(), "Berhasil", Toast.LENGTH_SHORT)
                                        .show()
                                    findNavController().navigate(R.id.action_inputCategoryFragment_to_homeFragment)
                                }

                                else -> {}
                            }
                        }
                }
            }
        }
    }
}