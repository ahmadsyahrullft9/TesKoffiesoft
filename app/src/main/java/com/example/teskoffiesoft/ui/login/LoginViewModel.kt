package com.example.teskoffiesoft.ui.login

import android.content.Context
import androidx.lifecycle.*
import com.example.teskoffiesoft.config.network.ApiClient
import com.example.teskoffiesoft.config.network.NetworkState
import com.example.teskoffiesoft.config.network.Status
import com.example.teskoffiesoft.data.PrefferenceManager
import com.example.teskoffiesoft.data.api.Account
import com.example.teskoffiesoft.data.models.LoginResponse
import com.example.teskoffiesoft.data.models.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class LoginViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(context) as T
    }
}

class LoginViewModel(val context: Context) : ViewModel() {

    private val client: Retrofit
    private val prefferenceManager: PrefferenceManager

    private var loginCall: Call<LoginResponse>? = null

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse

    init {
        val apiClient = ApiClient()
        client = apiClient.getClient()

        prefferenceManager = PrefferenceManager(context)
    }

    fun cancelAll() {
        loginCall?.cancel()
    }

    fun login(username: String, password: String) {
        loginCall =
            client.create(Account::class.java).login(username = username, password = password)
        loginCall?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (!call.isCanceled) {
                    if (response.isSuccessful && response.body() != null) {
                        _networkState.postValue(NetworkState.SUCCESS)
                        _loginResponse.postValue(response.body())
                    } else {
                        _networkState.postValue(
                            NetworkState(
                                Status.FAILED,
                                "failed, login is null ${response.errorBody()}"
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
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