package com.yelpexplorer.core.injection

import com.yelpexplorer.core.utils.Const
import com.yelpexplorer.core.utils.DataSource
import com.yelpexplorer.features.business.data.graphql.repository.BusinessGraphQLRepository
import com.yelpexplorer.features.business.data.rest.repository.BusinessRestRepository
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.features.business.domain.usecase.BusinessDetailsUseCase
import com.yelpexplorer.features.business.domain.usecase.BusinessListUseCase
import com.yelpexplorer.features.business.domain.usecase.GetBusinessDetailsUseCase
import com.yelpexplorer.features.business.domain.usecase.GetBusinessListUseCase
import com.yelpexplorer.features.business.presentation.businessdetails.BusinessDetailsViewModel
import com.yelpexplorer.features.business.presentation.businesslist.BusinessListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val businessModule = module {
    viewModel { BusinessListViewModel(get()) }
    viewModel { BusinessDetailsViewModel(get(), get()) }

    factory<BusinessListUseCase> { GetBusinessListUseCase(get()) }
    factory<BusinessDetailsUseCase> { GetBusinessDetailsUseCase(get()) }

    single<BusinessRepository> {
        when (Const.DATASOURCE) {
            DataSource.REST -> {
                BusinessRestRepository(get())
            }
            DataSource.GRAPHQL -> {
                BusinessGraphQLRepository(get())
            }
        }
    }
}
