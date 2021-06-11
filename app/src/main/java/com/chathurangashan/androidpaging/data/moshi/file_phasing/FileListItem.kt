package com.chathurangashan.androidpaging.data.moshi.file_phasing


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FileListItem(
    @Json(name = "file")
    val `file`: String,
    @Json(name = "file_type")
    val fileType: String,
    @Json(name = "modified_date")
    val modifiedDate: String,
    @Json(name = "name")
    val name: String
)