package com.yelpexplorer

import android.app.Application
import com.yelpexplorer.features.business.domain.injection.businessModule
import com.yelpexplorer.injection.appFlavorModule
import com.yelpexplorer.injection.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class YelpExplorer : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModules = listOf(appModule, appFlavorModule)
        val featureModules = listOf(businessModule)

        startKoin {
            androidContext(this@YelpExplorer)
            modules(appModules)
            modules(featureModules)
        }

        Timber.plant(DebugTree())
    }
}
