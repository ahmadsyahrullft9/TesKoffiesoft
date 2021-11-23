package com.example.teskoffiesoft.data.models

data class LoginResult(
    val account_status: StatusX,
    val email: String,
    val firstname: String,
    val grup: String,
    val hp: String,
    val id: Int,
    val jenis_kelamin: JenisKelamin,
    val lastname: String,
    val photo: Photo,
    val status: StatusX,
    val tgl_lahir: String,
    val toko: Toko
)