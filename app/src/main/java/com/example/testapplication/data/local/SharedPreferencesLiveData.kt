package com.example.testapplication.data.local

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.LiveData

abstract class SharedPreferenceLiveData<T>(
    var sharedPrefs: SharedPreferences,
    var key: String,
    var defValue: T
) : LiveData<T>() {

    private val preferenceChangeListener =
        OnSharedPreferenceChangeListener { _, key ->
            if (this@SharedPreferenceLiveData.key == key) {
                value = getValueFromPreferences(key, defValue)
            }
        }

    abstract fun getValueFromPreferences(key: String, defValue: T): T

    abstract fun setValueForPreferences(key: String, value: T)

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }

    fun getStringLiveData(
        key: String,
        defaultValue: String
    ): SharedPreferenceStringLiveData {
        return SharedPreferenceStringLiveData(sharedPrefs, key, defaultValue)
    }
}

class SharedPreferenceStringLiveData(prefs: SharedPreferences, key: String, defValue: String?) :
    SharedPreferenceLiveData<String?>(prefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: String?): String? =
        sharedPrefs.getString(key, defValue)

    override fun setValueForPreferences(key: String, value: String?) {
        sharedPrefs.edit().putString(key, value).apply()
    }

}