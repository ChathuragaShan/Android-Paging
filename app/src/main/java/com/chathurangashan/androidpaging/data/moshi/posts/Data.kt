package com.chathurangashan.androidpaging.data.moshi.posts


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "link")
    val link: String?,
    @Json(name = "owner")
    val owner: Owner,
    @Json(name = "publishDate")
    val publishDate: String,
    @Json(name = "tags")
    val tags: List<String>,
    @Json(name = "text")
    val text: String
)