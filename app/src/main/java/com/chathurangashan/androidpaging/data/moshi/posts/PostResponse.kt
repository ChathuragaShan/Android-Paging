package com.chathurangashan.androidpaging.data.moshi.posts


import com.chathurangashan.androidpaging.data.moshi.posts.Data
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostResponse(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "limit")
    val limit: Int,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "page")
    val page: Int,
    @Json(name = "total")
    val total: Int
)