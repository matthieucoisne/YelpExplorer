package com.yelpexplorer.features.business.domain.injection

import com.yelpexplorer.features.business.data.repository.BusinessDataRepository
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.features.business.domain.usecase.GetBusinessDetailsUseCase
import com.yelpexplorer.features.business.domain.usecase.GetBusinessListUseCase
import com.yelpexplorer.features.business.presentation.businessdetails.BusinessDetailsViewModel
import com.yelpexplorer.features.business.presentation.businesslist.BusinessListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val businessModule = module {
    viewModel { BusinessListViewModel(get()) }
    viewModel { BusinessDetailsViewModel(get()) }

    factory { GetBusinessListUseCase(get()) }
    factory { GetBusinessDetailsUseCase(get()) }

    single<BusinessRepository> { BusinessDataRepository(get()) }
}
