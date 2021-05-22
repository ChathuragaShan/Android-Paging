package com.chathurangashan.androidpaging.di

import android.content.Context
import com.chathurangashan.androidpaging.di.modules.AppModule
import com.chathurangashan.androidpaging.di.subcomponents.ActivitySubComponent
import com.chathurangashan.androidpaging.di.subcomponents.FragmentSubComponent
import com.chathurangashan.androidpaging.di.subcomponents.SubComponentModule
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [AssistedInjectModule::class, AppModule::class, SubComponentModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun activityComponent() : ActivitySubComponent.Factory
    fun fragmentComponent() : FragmentSubComponent.Factory

}

@AssistedModule
@Module(includes = [AssistedInject_AssistedInjectModule::class])
interface AssistedInjectModule

