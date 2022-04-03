package com.example.buddy.domain.models

data class Posts(
    var description : String ="",
    var imageUrl : String="",
    var creationTimeMs : Long =0,
    var user : UserFirebase?=null
)