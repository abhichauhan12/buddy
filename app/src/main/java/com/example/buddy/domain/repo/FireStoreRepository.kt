package com.example.buddy.domain.repo

import android.net.Uri
import com.example.buddy.data.firebase.Authentication
import com.example.buddy.data.firebase.FireStore
import com.example.buddy.domain.models.Posts
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreRepository private constructor(private val firestore: FireStore ) {

    companion object {
        @Volatile
        var INSTANCE: FireStoreRepository? = null

        fun get(): FireStoreRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = FireStoreRepository(firestore = FireStore.get())
                INSTANCE!!
            }
        }
    }

    fun postFromFirestore(onSuccess :(List<Posts>) ->Unit)=firestore.fetchPosts(onSuccess)

    fun userPostFromFireStore(onSuccess: (List<Posts>) -> Unit)=firestore.signInUserPost(onSuccess)

    fun uploadPostInFirebase(photoUri: Uri,onSuccess: () -> Unit,getPost : (String)-> Posts)=firestore.storingPostInFirebase(photoUri, onSuccess,getPost)
}