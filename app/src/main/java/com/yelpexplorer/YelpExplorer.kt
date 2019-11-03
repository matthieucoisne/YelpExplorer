package com.yelpexplorer

import com.yelpexplorer.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree

class YelpExplorer : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}
