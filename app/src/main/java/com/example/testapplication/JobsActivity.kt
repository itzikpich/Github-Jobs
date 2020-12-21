package com.example.testapplication

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.testapplication.di.components.JobsSubcomponent
import com.example.testapplication.models.GithubJob
import com.example.testapplication.utilities.addFragment
import com.example.testapplication.utilities.flipFragment
import com.example.testapplication.view_models.GithubJobsViewModel
import com.example.testapplication.views.FavoritesFragment
import com.example.testapplication.views.MainFragment
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Scope

// Definition of a custom scope called ActivityScope
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityScope

class JobsActivity : AppCompatActivity() {

    @Inject
    lateinit var githubJobsViewModel: GithubJobsViewModel

    lateinit var jobsSubcomponent: JobsSubcomponent

    companion object{
        const val FRAGMENT_CONTAINER = R.id.frameContainer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//      inject Dagger in the activity's onCreate() method before calling super.onCreate() to avoid issues with fragment restoration.
//        (applicationContext as MyApplication).appComponent.inject(this)
        jobsSubcomponent = (applicationContext as MyApplication).appComponent.jobsComponent().create()
        jobsSubcomponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        sharedPref = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (savedInstanceState == null) {
            this.addFragment(MainFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_fav -> {
                this.flipFragment(FavoritesFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.last() is MainFragment) finish()
        else super.onBackPressed()
    }

}