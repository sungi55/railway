package com.sunhurov.railway

import androidx.multidex.MultiDexApplication
import com.sunhurov.railway.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


open class App: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // CONFIGURATION ---
    open fun configureDi() =
        startKoin {
            //inject Android context
            androidContext(this@App)
            // use modules
            modules(provideComponent())
        }


    open fun provideComponent() = appComponent
}