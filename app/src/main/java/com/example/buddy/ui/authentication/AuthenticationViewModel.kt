package com.example.buddy.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buddy.domain.models.User
import com.example.buddy.domain.repo.AuthenticationRepository

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepository ):ViewModel() {

    fun signupUser(user: User,onSuccess : ()-> Unit , onFailure : ()-> Unit){
        authenticationRepository.signUpRepo(user,onSuccess,onFailure)
    }

    fun loginUser(user: User, onSuccess : ()-> Unit, onFailure : ()-> Unit){
        authenticationRepository.loginRepo(user, onSuccess, onFailure)
    }

    fun isUserLogin()=authenticationRepository.isLogin()

    fun logoutUser()=authenticationRepository.logout()

    class Factory(private val authenticationRepository: AuthenticationRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AuthenticationViewModel(authenticationRepository) as T
        }
    }
}