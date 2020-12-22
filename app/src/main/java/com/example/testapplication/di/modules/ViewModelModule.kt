package com.example.testapplication.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.view_models.GithubJobsViewModel
import com.example.testapplication.view_models.GithubJobsViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import java.lang.annotation.Documented
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GithubJobsViewModel::class) // PROVIDE YOUR OWN MODELS HERE
    internal abstract fun bindEditPlaceViewModel(editPlaceViewModel: GithubJobsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: GithubJobsViewModelFactory): ViewModelProvider.Factory
}

@Documented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)