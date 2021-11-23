package com.example.teskoffiesoft.data.models

data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val hp: String,
    val email: String,
    val grup: String,
    val jenis_kelamin: Int,
    val password: String,
    val tgl_lahir: String
)