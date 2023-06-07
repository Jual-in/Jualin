package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jualin.apps.data.Result
import com.jualin.apps.data.repositories.BusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val businessRepository: BusinessRepository
) : ViewModel() {

    private val _predictResult: MutableLiveData<Result<String>> = MutableLiveData()
    val predictResult: LiveData<Result<String>> get() = _predictResult

    fun predictImage(image: File) {
        businessRepository.predictImage(image).observeForever {
            _predictResult.postValue(it)
        }
    }
}