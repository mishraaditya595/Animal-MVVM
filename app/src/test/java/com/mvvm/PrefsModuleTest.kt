package com.mvvm

import android.app.Application
import com.mvvm.di.PrefsModule
import com.mvvm.util.SharedPreferencesHelper

class PrefsModuleTest(val mockPrefs: SharedPreferencesHelper): PrefsModule()
{
    override fun provideSharedPreferences(app: Application): SharedPreferencesHelper
    {
        return mockPrefs
    }
}