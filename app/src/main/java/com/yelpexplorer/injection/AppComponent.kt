package com.yelpexplorer.injection

import com.yelpexplorer.YelpExplorer
import com.yelpexplorer.features.business.data.BusinessDataModule
import com.yelpexplorer.features.business.domain.BusinessDomainModule
import com.yelpexplorer.injection.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilder::class,

    AppModule::class,
    NetworkModule::class,
    ViewModelModule::class,

    BusinessDataModule::class,
    BusinessDomainModule::class
])
interface AppComponent : AndroidInjector<YelpExplorer> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<YelpExplorer>()
}
