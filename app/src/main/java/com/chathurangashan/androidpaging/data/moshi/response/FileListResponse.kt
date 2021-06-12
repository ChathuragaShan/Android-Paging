package com.chathurangashan.androidpaging.data.moshi.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FileListResponse(
    @Json(name = "data")
    val `data`: List<Data>?,
    @Json(name = "message")
    val message : String?,
    @Json(name = "is_success")
    val isSuccess: Boolean,
    @Json(name = "page")
    val page: Int = 0,
    @Json(name = "total")
    val total: Int = 0
)