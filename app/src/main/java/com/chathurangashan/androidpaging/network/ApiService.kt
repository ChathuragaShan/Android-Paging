package com.chathurangashan.androidpaging.network

import com.chathurangashan.androidpaging.data.moshi.response.FileListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("files")
    suspend fun getPosts(@Query("page") page: Int, @Query("limit") limit: Int):
            Response<FileListResponse>
}