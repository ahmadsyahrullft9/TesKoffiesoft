package com.example.teskoffiesoft.ui.splashscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.teskoffiesoft.data.PrefferenceManager
import com.example.teskoffiesoft.data.models.RegisterRequest

class SplashscreenViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashscreenViewModel(context) as T
    }
}

class SplashscreenViewModel(val context: Context) : ViewModel() {
    private val prefferenceManager: PrefferenceManager

    val registerRequest: LiveData<RegisterRequest>

    init {
        prefferenceManager = PrefferenceManager(context)
        registerRequest = prefferenceManager.userPreffFlow.asLiveData()
    }
}