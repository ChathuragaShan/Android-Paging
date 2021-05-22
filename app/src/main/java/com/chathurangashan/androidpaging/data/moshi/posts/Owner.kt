package com.chathurangashan.androidpaging.data.moshi.posts


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "email")
    val email: String,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "picture")
    val picture: String,
    @Json(name = "title")
    val title: String
)