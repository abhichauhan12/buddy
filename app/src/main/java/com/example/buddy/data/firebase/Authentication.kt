package com.example.buddy.data.firebase

import com.example.buddy.domain.models.User
import com.google.firebase.auth.FirebaseAuth

class Authentication private constructor() {

    companion object {

        @Volatile
        var INSTANCE: Authentication? = null

        fun get(): Authentication {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Authentication()
                INSTANCE!!
            }
        }
    }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun signUp(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onSuccess()
                else onFailure()
            }
    }

    fun login(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                // Sign up successful
                if (task.isSuccessful) onSuccess()
                else onFailure()
            }
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun logout() = firebaseAuth.signOut()
}