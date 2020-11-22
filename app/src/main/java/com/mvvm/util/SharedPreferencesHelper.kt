package com.mvvm.util

import android.preference.PreferenceManager
import android.content.Context

class SharedPreferencesHelper(context: Context)
{
    private val PREF_API_KEY = "Api Key"
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveApiKey(key: String?)
    {
        prefs.edit().putString(PREF_API_KEY, key).apply()
    }

    fun getApiKey(): String?
    {
        return prefs.getString(PREF_API_KEY, null)
    }
}