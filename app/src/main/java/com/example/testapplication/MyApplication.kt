package com.example.testapplication

import android.app.Application
import com.example.testapplication.di.components.ApplicationComponent
import com.example.testapplication.di.components.DaggerApplicationComponent

class MyApplication: Application() {

    val appComponent = DaggerApplicationComponent.builder().create(this).build()

}