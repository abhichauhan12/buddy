package com.example.buddy.data.firebase

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.buddy.domain.models.Posts
import com.example.buddy.domain.models.User
import com.example.buddy.domain.models.UserFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage

private const val TAG = "FireStore"

class FireStore private constructor() {

    companion object {

        @Volatile
        var INSTANCE: FireStore? = null

        fun get(): FireStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = FireStore()
                INSTANCE!!
            }
        }
    }

    private val storageReference by lazy { FirebaseStorage.getInstance().reference }

    private val firebaseFireStore by lazy { FirebaseFirestore.getInstance() }

    fun fetchPosts(onSuccess: (List<Posts>) -> Unit) {
        val postReference = firebaseFireStore.collection("posts")
            .limit(20)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)

        postReference.addSnapshotListener { value, error ->
            if (error != null || value == null) {
                Log.e(TAG, "Error when post query", error)
                return@addSnapshotListener
            }


            val postList = value.toObjects(Posts::class.java)
            onSuccess(postList)
//            for (post in postList){
//                Log.i(TAG,"Post ${post}")
//
//            }
        }

    }

    fun signInUserPost(onSuccess: (List<Posts>) -> Unit) {
        var signInUSer: UserFirebase? = null
        firebaseFireStore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get()
            .addOnSuccessListener { userSnapshor ->
                signInUSer = userSnapshor.toObject(UserFirebase::class.java)
                Log.i(TAG, "Sign in user :  $signInUSer")
            }
            .addOnFailureListener { excetion ->
                Log.i(TAG, "Fail to fetch sign in user", excetion)
            }

        val postReference = firebaseFireStore.collection("posts")
            .limit(20)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)

        postReference.addSnapshotListener { value, error ->
            if (error != null || value == null) {
                Log.e(TAG, "Error when post query", error)
                return@addSnapshotListener
            }

            val userPostList = value.toObjects(Posts::class.java)
            onSuccess(userPostList)
        }
    }



    fun storingPostInFirebase(photoUri :Uri , onSuccess: () -> Unit, getPost : (String)-> Posts){
        val photoreference = storageReference.child("images/${System.currentTimeMillis()}-photo.jpg")
        photoreference.putFile(photoUri)
            .continueWithTask{ photoUploadingTask ->
                Log.i(TAG,"upload photo : ${photoUploadingTask.result?.bytesTransferred}")
                photoreference.downloadUrl
            }.continueWithTask { downloadUrlTask ->
                val post = getPost(downloadUrlTask.result.toString())


                firebaseFireStore.collection("posts").add(post)
            }.addOnCompleteListener { postCreatingTask ->
                if (!postCreatingTask.isSuccessful){
                    Log.e(TAG,"Exception on post uploading in firestore",postCreatingTask.exception)
//                    Toast.makeText(, "", Toast.LENGTH_SHORT).show()
                }
                onSuccess()

            }
    }


}