package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.data.repositories.BusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessDetailViewModel @Inject constructor(
    private val repository: BusinessRepository
) : ViewModel() {

    private val _dataStateFlow: MutableStateFlow<Result<Business>> =
        MutableStateFlow(Result.Loading)
    val dataStateFlow: StateFlow<Result<Business>> get() = _dataStateFlow

    fun getBusinessById(id: Int) {
        viewModelScope.launch {
            repository.getBusinessById(id)
                .map { Result.Success(it) }
                .catch { e ->
                    _dataStateFlow.value = Result.Error(e.message)
                }
                .collect { result ->
                    _dataStateFlow.value = result
                }
        }
    }
}
