package com.chathurangashan.androidpaging.di.subcomponents

import com.chathurangashan.androidpaging.di.modules.ActivityModule
import com.chathurangashan.androidpaging.di.scopes.ActivityScope
import com.chathurangashan.androidpaging.ui.activities.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivitySubComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance activity: MainActivity,
                   @BindsInstance hostFragment: Int): ActivitySubComponent
    }

    fun inject(MainActivity: MainActivity)
}