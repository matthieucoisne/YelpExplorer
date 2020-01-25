package com.yelpexplorer

import android.app.Application
import com.yelpexplorer.injection.AppModule
import com.yelpexplorer.libraries.core.injection.scope.ApplicationScope
import timber.log.Timber
import timber.log.Timber.DebugTree
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.ktp.KTP
import toothpick.smoothie.module.SmoothieApplicationModule

class YelpExplorer : Application() {

    private lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())

        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        }

        scope = KTP.openScope(ApplicationScope::class)
            .installModules(
                SmoothieApplicationModule(this),
                AppModule()
            )
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        scope.release()
    }
}
