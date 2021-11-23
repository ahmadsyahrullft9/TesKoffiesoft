package com.example.teskoffiesoft.data.preff

import androidx.datastore.preferences.core.stringPreferencesKey

object PreffKey {
    val FULLNAME = stringPreferencesKey("fullname")
    val EMAIL = stringPreferencesKey("email")
    val PHONE = stringPreferencesKey("phone")
    val PASSWORD = stringPreferencesKey("password")
}