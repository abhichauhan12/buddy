package com.example.buddy.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.buddy.R
import com.example.buddy.databinding.FragmentSplashBinding
import com.example.buddy.domain.repo.AuthenticationRepository
import kotlinx.coroutines.delay

class Splash : Fragment(R.layout.fragment_splash){
    private lateinit var binding: FragmentSplashBinding
    private val authenticationViewModel by lazy {
        ViewModelProvider(
            this, AuthenticationViewModel.Factory(authenticationRepository = AuthenticationRepository.get())
        )[AuthenticationViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        binding.lifecycleOwner = this

        val isUserLogin = authenticationViewModel.isUserLogin()

        lifecycleScope.launchWhenStarted {
            delay(2000)
            if (isUserLogin) {
                findNavController().navigate(R.id.action_splash_to_homeViewpager2)
            } else {
                findNavController().navigate(R.id.action_splash_to_signup)
            }
        }

    }


}