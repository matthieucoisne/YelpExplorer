package com.yelpexplorer.injection

import com.yelpexplorer.features.business.presentation.BusinessActivity
import com.yelpexplorer.features.business.presentation.businessdetails.BusinessDetailsFragment
import com.yelpexplorer.features.business.presentation.businesslist.BusinessListFragment
import com.yelpexplorer.features.settings.presentation.settings.SettingsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    internal abstract fun contributeBusinessActivity(): BusinessActivity

    @ContributesAndroidInjector
    internal abstract fun contributeBusinessListFragment(): BusinessListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeBusinessDetailsFragment(): BusinessDetailsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSettingsActivity(): SettingsActivity
}
