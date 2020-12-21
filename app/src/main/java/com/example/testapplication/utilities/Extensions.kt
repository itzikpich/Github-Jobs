package com.example.testapplication.utilities

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.testapplication.JobsActivity.Companion.FRAGMENT_CONTAINER
import com.example.testapplication.R
import java.text.ParseException
import java.text.SimpleDateFormat


fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int = FRAGMENT_CONTAINER) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int = FRAGMENT_CONTAINER) {
    if (!supportFragmentManager.fragments.isNullOrEmpty()) {
        if (!supportFragmentManager.fragments[0].toString().contains(
                fragment.toString().replaceAfter(
                    "Fragment",
                    ""
                )
            )
        ) {
            supportFragmentManager.inTransaction { replace(frameId, fragment) }
        }
    }
}

fun AppCompatActivity.flipFragment(fragment: Fragment, frameId: Int = FRAGMENT_CONTAINER) {
    if (!supportFragmentManager.fragments.isNullOrEmpty()) {
        if (!supportFragmentManager.fragments[0].toString().contains(
                fragment.toString().replaceAfter(
                    "Fragment",
                    ""
                )
            )
        ) {
            supportFragmentManager.inFlipTransaction { replace(frameId, fragment) }
        }
    }
}


private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction()
        .setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        .func()
        .addToBackStack(null).commit()
}

private inline fun FragmentManager.inFlipTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction()
        .setCustomAnimations(
            R.anim.card_flip_right_in,
            R.anim.card_flip_right_out,
            R.anim.card_flip_left_in,
            R.anim.card_flip_left_out
        )
        .func()
        .addToBackStack(null).commit()
}

/**
 * Extension method to load imageView from url.
 */
fun ImageView.loadFromUrlToGlide(imageUrl: String?, onResourceReady: () -> Unit = {}) {
    GlideApp.with(this).asBitmap().load(imageUrl).listener(object : RequestListener<Bitmap> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            onResourceReady.invoke()
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onResourceReady.invoke()
            return false
        }
    }).into(this)
}

fun String.githubJobTimeFormatter() : String {
    val githubJobsCreatedAtFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
    val requiredFormat = SimpleDateFormat("MMM-dd-yyyy")
    return try {
        val date = githubJobsCreatedAtFormat.parse(this)
        requiredFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}