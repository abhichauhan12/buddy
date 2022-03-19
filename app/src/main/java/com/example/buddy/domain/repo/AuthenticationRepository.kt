package com.example.buddy.domain.repo

import com.example.buddy.data.firebase.Authentication
import com.example.buddy.domain.models.User

class AuthenticationRepository private constructor(private val authencation :Authentication){

    companion object {
        @Volatile
        var INSTANCE: AuthenticationRepository? = null

        fun get(): AuthenticationRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = AuthenticationRepository(authencation = Authentication.get())
                INSTANCE!!
            }
        }
    }

    fun signUpRepo(user: User, onSuccess: () -> Unit, onFailure: () -> Unit){
        authencation.signUp(user, onSuccess, onFailure)
    }

    fun loginRepo(user: User,onSuccess: () -> Unit, onFailure: () -> Unit){
        authencation.login(user, onSuccess, onFailure)
    }

    fun logout(){
        authencation.logout()
    }

    fun isLogin()=authencation.isUserLoggedIn()



}