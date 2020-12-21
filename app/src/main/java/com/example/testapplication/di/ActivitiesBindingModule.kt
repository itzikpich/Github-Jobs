package com.example.testapplication.di

import com.example.testapplication.JobsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector
    abstract fun jobsActivity(): JobsActivity

}