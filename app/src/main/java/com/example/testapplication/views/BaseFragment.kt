package com.example.testapplication.views

import androidx.fragment.app.Fragment
import com.example.testapplication.JobsActivity

open class BaseFragment(layoutRes:Int): Fragment(layoutRes) {

    open val jobsActivity get() = activity as JobsActivity

}