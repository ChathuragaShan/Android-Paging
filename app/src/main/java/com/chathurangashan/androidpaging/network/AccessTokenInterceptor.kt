package com.chathurangashan.androidpaging.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AccessTokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()

        val authenticatedRequest = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("app-id", "600feddbf961a64ee95700c9")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}