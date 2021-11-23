package com.example.teskoffiesoft.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.teskoffiesoft.R
import com.example.teskoffiesoft.config.ui.BindingFragment
import com.example.teskoffiesoft.databinding.FragmentHomeBinding

class HomeFragment : BindingFragment<FragmentHomeBinding>() {

    private lateinit var homeViewModel: HomeViewModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupView(binding: FragmentHomeBinding) {
        homeViewModel = HomeViewModelFactory(requireContext()).create(HomeViewModel::class.java)
        homeViewModel.apply {
            registerRequest.observe(requireActivity()) { rRequest ->
                binding.txtGreeting.text = "Welcome ${rRequest.firstname} !"
            }
        }
        binding.btnLogout.setOnClickListener {
            val builder =
                AlertDialog.Builder(requireContext()).setTitle("Exit Confirmation")
                    .setMessage("Apakah anda ingin keluar akun?")
                    .setPositiveButton("Sign out", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            homeViewModel.resetRegisterRequest()
                            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSplashscreenFragment())
                            findNavController().popBackStack(R.id.homeFragment, true)
                        }
                    }).setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog?.dismiss()
                        }
                    }).setCancelable(false)
            val dialog = builder.create()
            dialog.show()
        }
    }
}