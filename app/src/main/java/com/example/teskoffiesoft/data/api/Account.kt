package com.example.teskoffiesoft.data.api

import com.example.teskoffiesoft.data.models.LoginResponse
import com.example.teskoffiesoft.data.models.RegisterRequest
import com.example.teskoffiesoft.data.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Account {

    @POST("users")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<LoginResponse>
}