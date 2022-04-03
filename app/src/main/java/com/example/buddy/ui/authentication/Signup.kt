package com.example.buddy.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.buddy.R
import com.example.buddy.databinding.FragmentSignupBinding
import com.example.buddy.domain.models.User
import com.example.buddy.domain.repo.AuthenticationRepository

class Signup : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding
    private val authenticationViewModel by lazy {
        ViewModelProvider(
            this, AuthenticationViewModel.Factory(authenticationRepository = AuthenticationRepository.get())
        )[AuthenticationViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSignupBinding.bind(view)
        binding.lifecycleOwner=this

        binding.submitButtonSignup.setOnClickListener{proceedSignUp()}

        binding.loginSignupLayout.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_login)
        }
    }
    private fun proceedSignUp() {
        val email = binding.emailEdittextSignup.text.toString()
        val password = binding.passwordEdittextSignup.text.toString()
        val user= User(email,password)
        authenticationViewModel.signupUser(
            user=user,
            onSuccess ={
                Toast.makeText(requireContext(), "SignUp successful, please Login!!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signup_to_login)
            },
            onFailure = {
                Toast.makeText(requireContext(), "SignUp failed", Toast.LENGTH_SHORT)
                    .show()
            }
        )
    }
}