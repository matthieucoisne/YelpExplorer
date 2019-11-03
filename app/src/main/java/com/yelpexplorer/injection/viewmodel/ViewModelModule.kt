package com.yelpexplorer.injection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yelpexplorer.features.business.presentation.businessdetails.BusinessDetailsViewModel
import com.yelpexplorer.features.business.presentation.businesslist.BusinessListViewModel
import com.yelpexplorer.libraries.core.injection.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(BusinessListViewModel::class)
    internal abstract fun bindBusinessListViewModel(businessListViewModel: BusinessListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BusinessDetailsViewModel::class)
    internal abstract fun bindBusinessDetailsViewModel(businessDetailsViewModel: BusinessDetailsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
