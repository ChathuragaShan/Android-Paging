package com.chathurangashan.androidpaging.di.subcomponents

import android.view.View
import com.chathurangashan.androidpaging.di.modules.FragmentModule
import com.chathurangashan.androidpaging.di.scopes.FragmentScope
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentSubComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance view: View): FragmentSubComponent
    }

}