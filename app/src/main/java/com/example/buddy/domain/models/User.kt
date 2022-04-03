package com.example.buddy.domain.models


//authentication
data class User(
    var email : String,
    var password : String
)

//firebase
data class UserFirebase(
    var username : String ="",
    var age :Int = 0
)
