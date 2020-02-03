package com.yelpexplorer

import android.app.Application
import com.yelpexplorer.features.business.domain.injection.BusinessModule
import com.yelpexplorer.injection.AppModule
import com.yelpexplorer.injection.FlavorModule
import com.yelpexplorer.libraries.core.injection.scope.ApplicationScope
import timber.log.Timber
import timber.log.Timber.DebugTree
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.smoothie.module.SmoothieApplicationModule

class YelpExplorer : Application() {

    private lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())

        scope = KTP.openScope(ApplicationScope::class)
            .installModules(
                SmoothieApplicationModule(this),
                AppModule(),
                FlavorModule(),
                BusinessModule()
            )
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        scope.release()
    }
}
