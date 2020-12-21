package com.example.testapplication.di.components

import com.example.testapplication.ActivityScope
import com.example.testapplication.JobsActivity
import com.example.testapplication.views.FavoritesFragment
import com.example.testapplication.views.MainFragment
import com.example.testapplication.views.JobDetailsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface JobsSubcomponent {

    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): JobsSubcomponent
    }

    /** This tells Dagger that JobsActivity requests injection from JobsComponent
    * so that this subcomponent graph needs to satisfy all the dependencies of the
    * fields that JobsActivity is injecting
    */
    fun inject(activity: JobsActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: JobDetailsFragment)
}