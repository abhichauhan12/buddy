package com.example.buddy.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buddy.domain.models.Posts
import com.example.buddy.domain.repo.FireStoreRepository

class ProfileViewModel(private val fireStoreRepository: FireStoreRepository) : ViewModel() {

    fun userPostFromFirebase(onSuccess :(List<Posts>) ->Unit) = fireStoreRepository.userPostFromFireStore(onSuccess)


    class Factory(private val fireStoreRepository: FireStoreRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(fireStoreRepository) as T
        }
    }

}