package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jualin.apps.data.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InputCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    fun getAllCategories() = categoryRepository.getAllCategories()

    fun selectCategories(categoryIds: List<Int>) = categoryRepository.selectCategories(categoryIds)

}