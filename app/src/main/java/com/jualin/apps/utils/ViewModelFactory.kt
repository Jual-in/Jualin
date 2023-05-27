package com.jualin.apps.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jualin.apps.data.Repository
import com.jualin.apps.di.Injection
import com.jualin.apps.ui.viewmodel.LoginViewModel
import com.jualin.apps.ui.viewmodel.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val Repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Repository) as T
            }

                else -> throw IllegalArgumentException("Unknown ViewModel class: "+modelClass.name)
            }
        }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(Context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(Context))
            }.also { instance = it }
    }
    }