package com.example.teskoffiesoft.data.models

data class Toko(
    val deskripsi: String,
    val email: String,
    val id: Int,
    val jalan: String,
    val kecamatan: String,
    val kelurahan: String,
    val kodepos: String,
    val kota: String,
    val latitude: Int,
    val logo: List<Logo>,
    val longitude: Int,
    val nama: String,
    val provinsi: String,
    val slogan: String,
    val status: StatusX,
    val telp: String
)