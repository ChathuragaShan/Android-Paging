package com.chathurangashan.androidpaging.network

import com.chathurangashan.androidpaging.data.moshi.posts.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("post")
    suspend fun getPosts(@Query("page") page: Int, @Query("limit") limit: Int):
            Response<PostResponse>
}