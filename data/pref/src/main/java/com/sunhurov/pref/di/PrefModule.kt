package com.sunhurov.pref.di

import android.content.Context
import android.content.SharedPreferences
import com.sunhurov.pref.AppPreferencesHelper
import com.sunhurov.pref.PreferenceHelper
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext


val prefModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    }

    factory<PreferenceHelper> { AppPreferencesHelper(get()) }

}