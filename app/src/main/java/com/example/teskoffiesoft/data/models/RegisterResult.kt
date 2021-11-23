package com.example.teskoffiesoft.data.models

data class RegisterResult(
    val account_status: StatusX,
    val email: String,
    val firstname: String,
    val grup: String,
    val hp: String,
    val id: Int,
    val jenis_kelamin: Int,
    val lastname: String,
    val photo: Photo,
    val status: Status,
    val tgl_lahir: String,
    val toko: Toko
)