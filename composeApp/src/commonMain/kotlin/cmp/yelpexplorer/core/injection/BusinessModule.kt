package cmp.yelpexplorer.core.injection

import cmp.yelpexplorer.core.utils.Const
import cmp.yelpexplorer.core.utils.DataSource
import cmp.yelpexplorer.features.business.data.graphql.repository.BusinessGraphQLRepository
import cmp.yelpexplorer.features.business.data.rest.datasource.remote.BusinessApi
import cmp.yelpexplorer.features.business.data.rest.datasource.remote.RestBusinessApi
import cmp.yelpexplorer.features.business.data.rest.repository.BusinessRestRepository
import cmp.yelpexplorer.features.business.domain.repository.BusinessRepository
import cmp.yelpexplorer.features.business.domain.usecase.BusinessDetailsUseCase
import cmp.yelpexplorer.features.business.domain.usecase.BusinessListUseCase
import cmp.yelpexplorer.features.business.domain.usecase.GetBusinessDetailsUseCase
import cmp.yelpexplorer.features.business.domain.usecase.GetBusinessListUseCase
import cmp.yelpexplorer.features.business.presentation.businessdetails.BusinessDetailsViewModel
import cmp.yelpexplorer.features.business.presentation.businesslist.BusinessListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val businessModule = module {
    viewModelOf(::BusinessListViewModel)
    viewModelOf(::BusinessDetailsViewModel)

    factoryOf(::GetBusinessListUseCase).bind(BusinessListUseCase::class)
    factoryOf(::GetBusinessDetailsUseCase).bind(BusinessDetailsUseCase::class)

    when (Const.DATASOURCE) {
        DataSource.REST -> {
            single<BusinessRepository> {
                BusinessRestRepository(
                    api = get(),
                    dispatcher = get(named(Const.DISPATCHER_DEFAULT))
                )
            }
            singleOf(::RestBusinessApi).bind(BusinessApi::class)
        }
        DataSource.GRAPHQL -> {
            single<BusinessRepository> {
                BusinessGraphQLRepository(
                    apolloClient = get(),
                    dispatcher = get(named(Const.DISPATCHER_DEFAULT))
                )
            }
        }
    }
}
