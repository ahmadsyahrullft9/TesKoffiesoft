package com.example.teskoffiesoft

import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.teskoffiesoft.config.ui.BindingActivity
import com.example.teskoffiesoft.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(){

    private val TAG = "MainActivity"

    private lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setupView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}