package com.chathurangashan.androidpaging

import android.app.Application
import com.chathurangashan.androidpaging.data.enums.BuildType
import com.chathurangashan.androidpaging.di.DaggerApplicationComponent
import com.chathurangashan.androidpaging.di.InjectorProvider
import com.facebook.stetho.Stetho

class ThisApplication : Application(), InjectorProvider{

    companion object {
        val buildType: BuildType = BuildType.RELEASE
    }

    override val component by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}