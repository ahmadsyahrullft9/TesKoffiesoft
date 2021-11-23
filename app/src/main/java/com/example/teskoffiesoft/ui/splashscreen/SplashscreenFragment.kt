package com.example.teskoffiesoft.ui.splashscreen

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.teskoffiesoft.R
import com.example.teskoffiesoft.config.ui.BindingFragment
import com.example.teskoffiesoft.data.models.RegisterRequest
import com.example.teskoffiesoft.databinding.FragmentSplashscreenBinding
import kotlin.concurrent.thread

@SuppressLint("CustomSplashScreen")
class SplashscreenFragment : BindingFragment<FragmentSplashscreenBinding>() {

    private lateinit var splashscreenViewModel: SplashscreenViewModel
    private val max = 5
    private var count = 0
    private var registerRequest: RegisterRequest? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashscreenBinding
        get() = FragmentSplashscreenBinding::inflate

    override fun setupView(binding: FragmentSplashscreenBinding) {
        splashscreenViewModel =
            SplashscreenViewModelFactory(requireContext()).create(SplashscreenViewModel::class.java)
        splashscreenViewModel.registerRequest.observe(requireActivity()) {
            if (it != null)
                registerRequest = it
        }
        thread {
            try {
                while (count < max) {
                    count += 1
                    Thread.sleep(80)
                }
            } finally {
                requireActivity().runOnUiThread {
                    check()
                }
            }
        }
    }

    private fun check() {
        if (registerRequest == null || TextUtils.isEmpty(registerRequest?.email)) {
            //navigate to login
            findNavController().navigate(SplashscreenFragmentDirections.actionSplashscreenFragmentToLoginFragment())
        } else {
            //navigate to home
            findNavController().navigate(SplashscreenFragmentDirections.actionSplashscreenFragmentToHomeFragment())
        }
        findNavController().popBackStack(R.id.splashscreenFragment, true)
    }
}