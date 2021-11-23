package com.example.teskoffiesoft.data.models

data class LoginResponse(
    val access_token: String,
    val status: Status,
    val token_type: String,
    val data: LoginResult?
)