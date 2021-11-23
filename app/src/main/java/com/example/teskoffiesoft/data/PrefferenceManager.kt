package com.example.teskoffiesoft.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.teskoffiesoft.data.preff.PreffKey
import kotlinx.coroutines.flow.mapLatest
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.teskoffiesoft.data.models.RegisterRequest

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class PrefferenceManager(val context: Context) {

    val userPreffFlow = context.dataStore.data.mapLatest { pref ->
        val fullname: String =
            if (pref[PreffKey.FULLNAME] != null) pref[PreffKey.FULLNAME].toString() else ""
        val email: String =
            if (pref[PreffKey.EMAIL] != null) pref[PreffKey.EMAIL].toString() else ""
        val phone: String =
            if (pref[PreffKey.PHONE] != null) pref[PreffKey.PHONE].toString() else ""
        val password: String =
            if (pref[PreffKey.PASSWORD] != null) pref[PreffKey.PASSWORD].toString() else ""

        val registerRequest = RegisterRequest(
            firstname = fullname,
            lastname = "",
            hp = phone,
            email = email,
            grup = "",
            jenis_kelamin = 1,
            password = password,
            tgl_lahir = ""
        )
        registerRequest
    }

    suspend fun updateRegisterRequest(registerRequest: RegisterRequest) {
        context.dataStore.edit { pref ->
            pref[PreffKey.FULLNAME] = registerRequest.firstname
            pref[PreffKey.EMAIL] = registerRequest.email
            pref[PreffKey.PHONE] = registerRequest.hp
            pref[PreffKey.PASSWORD] = registerRequest.password
        }
    }

    suspend fun resetRegisterRequest() {
        context.dataStore.edit { pref ->
            pref.clear()
        }
    }
}