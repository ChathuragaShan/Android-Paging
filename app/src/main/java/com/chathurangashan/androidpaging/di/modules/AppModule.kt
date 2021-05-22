package com.chathurangashan.androidpaging.di.modules

import com.chathurangashan.androidpaging.network.ApiService
import com.chathurangashan.androidpaging.network.NetworkService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit() = NetworkService.getInstance().getService(ApiService::class.java)
}