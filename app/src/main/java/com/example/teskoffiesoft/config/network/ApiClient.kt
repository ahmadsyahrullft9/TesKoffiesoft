package com.example.teskoffiesoft.config.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val BASE_URL = "http://202.157.184.201:8000/"

    fun getClient(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(BuildOkHTTPClient(true).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}