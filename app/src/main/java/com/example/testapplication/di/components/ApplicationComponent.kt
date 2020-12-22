package com.example.testapplication.di.components

import android.app.Application
import android.content.SharedPreferences
import com.example.testapplication.MyApplication
import com.example.testapplication.di.ActivitiesBindingModule
import com.example.testapplication.di.modules.LocalDataSourceModule
import com.example.testapplication.di.modules.NetworkModule
import com.example.testapplication.di.modules.SubcomponentsModule
import com.example.testapplication.di.modules.ViewModelModule
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    NetworkModule::class,
    LocalDataSourceModule::class,
    SubcomponentsModule::class,
    ViewModelModule::class,
    ActivitiesBindingModule::class,
    AndroidSupportInjectionModule::class
])
interface ApplicationComponent: AndroidInjector<MyApplication> {
//    This function exposes the JobsComponent Factory
//    out of the graph so consumers
//    can use it to obtain new instances of JobsComponent
    fun jobsComponent(): JobsSubcomponent.Factory
//    no need to inject activity directly as it comes from JobsSubcomponent
//    fun inject(activity: JobsActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder
        fun build(): ApplicationComponent
    }

}