package com.chathurangashan.androidpaging.di.modules

import androidx.navigation.Navigation
import com.chathurangashan.androidpaging.di.scopes.ActivityScope
import com.chathurangashan.androidpaging.ui.activities.MainActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @ActivityScope
    @Provides
    fun navigation(activity: MainActivity, hosFragment: Int) =
        Navigation.findNavController(activity,hosFragment)
}