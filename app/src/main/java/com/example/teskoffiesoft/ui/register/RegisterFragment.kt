package com.example.teskoffiesoft.ui.register

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.teskoffiesoft.config.ui.BindingFragment
import com.example.teskoffiesoft.databinding.FragmentRegisterBinding
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.teskoffiesoft.R
import com.example.teskoffiesoft.config.network.NetworkState
import com.example.teskoffiesoft.data.models.RegisterRequest
import com.example.teskoffiesoft.ui.LoadingDialog
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : BindingFragment<FragmentRegisterBinding>() {

    private lateinit var registerViewModel: RegisterViewModel
    private var registerRequest: RegisterRequest? = null
    private lateinit var loadingDialog: LoadingDialog

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate

    override fun setupView(binding: FragmentRegisterBinding) {
        registerViewModel =
            RegisterViewModelFactory(requireContext()).create(RegisterViewModel::class.java)
        loadingDialog = LoadingDialog(requireContext())

        binding.apply {
            registerViewModel.apply {
                networkState.observe(requireActivity()) {
                    when (it) {
                        NetworkState.LOADING -> showLoading(true)
                        NetworkState.SUCCESS -> showLoading(false)
                        NetworkState.ERROR -> {
                            showLoading(false)
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }

                registerResponse.observe(requireActivity()) { registerResponse ->
                    if (registerResponse != null) {
                        if (registerRequest != null) saveRegisterRequest(registerRequest!!)

                        Toast.makeText(requireContext(), "register success", Toast.LENGTH_LONG)
                            .show()

                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                        findNavController().popBackStack(R.id.registerFragment, true)
                    }
                }
            }


            edFirstname.addTextChangedListener { edFirstname.error = null }
            edEmail.addTextChangedListener { edEmail.error = null }
            edPhone.addTextChangedListener { edPhone.error = null }
            edPassword.addTextChangedListener { edPassword.error = null }

            btnRegister.setOnClickListener {
                if (TextUtils.isEmpty(edFirstname.text)) {
                    edFirstname.error = "this field is required"
                    edFirstname.requestFocus()
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(edEmail.text)) {
                    edEmail.error = "this field is required"
                    edEmail.requestFocus()
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(edPhone.text)) {
                    edPhone.error = "this field is required"
                    edPhone.requestFocus()
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(edPassword.text)) {
                    edPassword.error = "this field is required"
                    edPassword.requestFocus()
                    return@setOnClickListener
                }

                proccess_register(
                    fullname = edFirstname.text.toString(),
                    email = edEmail.text.toString(),
                    phone = edPhone.text.toString(),
                    password = edPassword.text.toString()
                )
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        try {
            if (loading) loadingDialog.show()
            else loadingDialog.dismiss()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun proccess_register(
        fullname: String,
        email: String,
        phone: String,
        password: String
    ) {
        registerRequest = RegisterRequest(
            firstname = fullname,
            lastname = "",
            hp = phone,
            email = email,
            grup = "member",
            jenis_kelamin = 1,
            password = password,
            tgl_lahir = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                .toString()
        )
        registerViewModel.register(registerRequest!!)
    }
}