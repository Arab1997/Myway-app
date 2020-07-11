package com.snatap.myway.app

import androidx.multidex.MultiDexApplication
import com.snatap.myway.BuildConfig
import com.snatap.myway.di.networkModule
import com.snatap.myway.di.sharedPrefModule
import com.snatap.myway.di.viewModelModule
import com.snatap.myway.utils.preferences.SharedManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : MultiDexApplication() {

    val sharedManager: SharedManager by inject()

    companion object {
        private lateinit var app: App
        fun get(): App =
            app
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        initFirebase()

        initLogger()

        initKoin()
    }

    private fun initFirebase(){
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    private fun initKoin() {

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    sharedPrefModule
                )
            )
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}