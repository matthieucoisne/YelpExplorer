package com.yelpexplorer.features.business.data

import com.apollographql.apollo.ApolloClient
import com.yelpexplorer.features.business.data.repository.BusinessDataRepository
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BusinessDataModule {

    @Provides
    @Singleton
    fun provideBusinessRepository(apolloClient: ApolloClient): BusinessRepository {
        return BusinessDataRepository(apolloClient)
    }
}
