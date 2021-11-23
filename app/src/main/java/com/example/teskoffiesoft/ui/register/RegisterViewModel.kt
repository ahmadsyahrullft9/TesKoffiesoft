package com.example.teskoffiesoft.ui.register

import android.content.Context
import androidx.lifecycle.*
import com.example.teskoffiesoft.config.network.ApiClient
import com.example.teskoffiesoft.config.network.NetworkState
import com.example.teskoffiesoft.config.network.Status
import com.example.teskoffiesoft.data.PrefferenceManager
import com.example.teskoffiesoft.data.api.Account
import com.example.teskoffiesoft.data.models.RegisterRequest
import com.example.teskoffiesoft.data.models.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RegisterViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(context) as T
    }
}

class RegisterViewModel(val context: Context) : ViewModel() {

    private val client: Retrofit
    private val prefferenceManager: PrefferenceManager

    private var registerCall: Call<RegisterResponse>? = null

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse>
        get() = _registerResponse

    init {
        val apiClient = ApiClient()
        client = apiClient.getClient()

        prefferenceManager = PrefferenceManager(context)
    }

    fun cancelAll(){
        registerCall?.cancel()
    }

    fun register(registerRequest: RegisterRequest) {
        registerCall = client.create(Account::class.java).register(registerRequest)
        registerCall?.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (!call.isCanceled) {
                    if (response.isSuccessful && response.body() != null) {
                        _networkState.postValue(NetworkState.SUCCESS)
                        _registerResponse.postValue(response.body())
                    } else {
                        _networkState.postValue(
                            NetworkState(
                                Status.FAILED,
                                "failed, register is null ${response.errorBody()}"
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                if (!call.isCanceled) {
                    t.printStackTrace()
                    _networkState.postValue(
                        NetworkState(
                            Status.FAILED,
                            "failed, request error"
                        )
                    )
                }
            }

        })
    }

    fun saveRegisterRequest(registerRequest: RegisterRequest) = viewModelScope.launch {
        prefferenceManager.updateRegisterRequest(registerRequest)
    }

    fun resetRegisterRequest() = viewModelScope.launch {
        prefferenceManager.resetRegisterRequest()
    }
}