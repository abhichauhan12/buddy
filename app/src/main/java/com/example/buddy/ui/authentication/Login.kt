package com.example.buddy.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.buddy.R
import com.example.buddy.databinding.FragmentLoginBinding
import com.example.buddy.domain.models.User
import com.example.buddy.domain.repo.AuthenticationRepository

class Login : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val authenticationViewModel by lazy {
        ViewModelProvider(
            this, AuthenticationViewModel.Factory(authenticationRepository = AuthenticationRepository.get())
        )[AuthenticationViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentLoginBinding.bind(view)
        binding.lifecycleOwner=this

        binding.loginButton.setOnClickListener { proceedLogin() }

        binding.signupLoginLayout.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signup)
        }

    }

    private fun proceedLogin() {
        val email = binding.emailEdittextLogin.text.toString()
        val password = binding.passwordEdittextLogin.text.toString()

        val user = User(email,password)
        authenticationViewModel.loginUser(
            user=user,
            onSuccess = {
                Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_login_to_homeActivity)
            },
            onFailure = {
                Toast.makeText(requireContext(), "User authentication failed", Toast.LENGTH_SHORT).show()
            }
        )


    }
}