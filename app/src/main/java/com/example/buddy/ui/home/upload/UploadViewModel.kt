package com.example.buddy.ui.home.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buddy.domain.models.Posts
import com.example.buddy.domain.repo.FireStoreRepository

class UploadViewModel(private val fireStoreRepository: FireStoreRepository) : ViewModel() {

    fun uploadPostInFirebase(photoUri: Uri,onSuccess: () -> Unit,getPost : (String)-> Posts) = fireStoreRepository.uploadPostInFirebase(photoUri, onSuccess,getPost)

    class Factory(private val fireStoreRepository: FireStoreRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UploadViewModel(fireStoreRepository) as T
        }
    }
}