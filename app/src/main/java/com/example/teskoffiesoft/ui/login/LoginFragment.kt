package com.example.teskoffiesoft.ui.login

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.teskoffiesoft.R
import com.example.teskoffiesoft.config.network.NetworkState
import com.example.teskoffiesoft.config.ui.BindingFragment
import com.example.teskoffiesoft.data.models.LoginResult
import com.example.teskoffiesoft.data.models.RegisterRequest
import com.example.teskoffiesoft.databinding.FragmentLoginBinding
import com.example.teskoffiesoft.ui.LoadingDialog
import java.lang.NullPointerException

class LoginFragment : BindingFragment<FragmentLoginBinding>() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loadingDialog: LoadingDialog

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setupView(binding: FragmentLoginBinding) {
        loginViewModel = LoginViewModelFactory(requireContext()).create(LoginViewModel::class.java)
        loadingDialog = LoadingDialog(requireContext())

        binding.apply {

            loginViewModel.apply {
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
                loginResponse.observe(requireActivity()) { loginResponse ->
                    if (loginResponse != null) {
                        val loginResult: LoginResult? = loginResponse.data
                        val registerRequest = RegisterRequest(
                            firstname = loginResult?.firstname.toString(),
                            lastname = loginResult?.lastname.toString(),
                            email = edEmail.text.toString(),
                            hp = loginResult?.hp.toString(),
                            password = edPassword.text.toString(),
                            grup = "",
                            jenis_kelamin = 1,
                            tgl_lahir = ""
                        )
                        saveRegisterRequest(registerRequest)

                        Toast.makeText(requireContext(), "register success", Toast.LENGTH_LONG)
                            .show()

                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        findNavController().popBackStack(R.id.loginFragment, true)
                    }
                }
            }

            edEmail.addTextChangedListener { edEmail.error = null }

            edPassword.addTextChangedListener { edPassword.error = null }

            btnLogin.setOnClickListener {
                if (TextUtils.isEmpty(edEmail.text)) {
                    edEmail.error = "this field is required"
                    edEmail.requestFocus()
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(edPassword.text)) {
                    edPassword.error = "this field is required"
                    edPassword.requestFocus()
                    return@setOnClickListener
                }

                process_login(edEmail.text.toString(), edPassword.text.toString())
            }

            btnRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                findNavController().popBackStack(R.id.loginFragment, true)
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

    private fun process_login(email: String, password: String) {
        loginViewModel.login(username = email, password = password)
    }
}