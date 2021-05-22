package com.chathurangashan.androidpaging.data.general.post

data class Item(
    val postId: String,
    val userId: String,
    val userName :String,
    val userEmail: String,
    val userProfilePictureUrl: String,
    val ImageUrl: String,
    val tags: List<String>,
    val text: String,
    val likes: Int,
    val postDate: String
)