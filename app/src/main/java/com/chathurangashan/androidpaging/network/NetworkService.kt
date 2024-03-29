package com.chathurangashan.androidpaging.network

import com.chathurangashan.androidpaging.ThisApplication
import com.chathurangashan.androidpaging.data.enums.BuildType
import java.util.concurrent.TimeUnit
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class NetworkService {

    private var baseURL: String = "https://www.dummyurl.com"

    companion object {
        fun getInstance(): NetworkService {
            return NetworkService()
        }

        fun getTestInstance(testUrl: HttpUrl): NetworkService {
            return NetworkService(testUrl)
        }
    }

    constructor() {
        baseURL = when (ThisApplication.buildType) {
            BuildType.RELEASE -> baseURL
            BuildType.DEVELOPMENT -> baseURL
            BuildType.TESTING -> baseURL
        }
    }

    constructor(testUrl: HttpUrl) : this() {
        baseURL = testUrl.toString()
    }

    fun <S> getService(serviceClass: Class<S>): S {

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(MockInterceptor())

        val builder = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseURL)
            .client(httpClient.build())

        val retrofit = builder.build()

        return retrofit.create(serviceClass)
    }
}