package com.example.buddy.ui.home.feeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buddy.domain.models.Posts
import com.example.buddy.domain.repo.FireStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FeedsViewModel(private val fireStoreRepository: FireStoreRepository) : ViewModel() {

    private val _fetchPost = MutableStateFlow<List<Posts>>(ArrayList())
    val fetchpost = _fetchPost.asStateFlow()

//    fun postFromFirebase(onSuccess: (List<Posts>) -> Unit) {
//        fireStoreRepository.postFromFirestore { list ->
//            val listOfpost: List<Posts> = list
//            _fetchPost.value = listOfpost
//        }
//    }

    fun postFromFirebase(onSuccess: (List<Posts>) -> Unit) {
        fireStoreRepository.postFromFirestore { list ->
            val listOfpost: List<Posts> = list
            _fetchPost.value = listOfpost
        }
    }


    class Factory(private val fireStoreRepository: FireStoreRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FeedsViewModel(fireStoreRepository) as T
        }
    }
}