package com.example.teskoffiesoft.ui.home

import android.content.Context
import androidx.lifecycle.*
import com.example.teskoffiesoft.data.PrefferenceManager
import com.example.teskoffiesoft.data.models.RegisterRequest
import kotlinx.coroutines.launch


class HomeViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(context) as T
    }
}

class HomeViewModel(val context: Context) : ViewModel() {

    private val prefferenceManager: PrefferenceManager

    val registerRequest: LiveData<RegisterRequest>

    init {
        prefferenceManager = PrefferenceManager(context)
        registerRequest = prefferenceManager.userPreffFlow.asLiveData()
    }

    fun saveRegisterRequest(registerRequest: RegisterRequest) = viewModelScope.launch {
        prefferenceManager.updateRegisterRequest(registerRequest)
    }

    fun resetRegisterRequest() = viewModelScope.launch {
        prefferenceManager.resetRegisterRequest()
    }
}