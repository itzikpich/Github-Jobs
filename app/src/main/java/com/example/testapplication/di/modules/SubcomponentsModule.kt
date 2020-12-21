package com.example.testapplication.di.modules

import com.example.testapplication.di.components.JobsSubcomponent
import dagger.Module

// The "subcomponents" attribute in the @Module annotation tells Dagger what
// Subcomponents are children of the Component this module is included in.
@Module(subcomponents = [JobsSubcomponent::class])
class SubcomponentsModule {
}