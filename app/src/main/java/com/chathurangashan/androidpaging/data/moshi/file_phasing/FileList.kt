package com.chathurangashan.androidpaging.data.moshi.file_phasing

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FileList(
    @Json(name = "files")
    val files: List<FileListItem>
)