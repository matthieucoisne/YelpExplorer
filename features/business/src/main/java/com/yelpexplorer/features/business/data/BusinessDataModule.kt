package com.yelpexplorer.features.business.data

import com.yelpexplorer.features.business.data.repository.BusinessDataRepository
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BusinessDataModule {

    @Provides
    @Singleton
    fun provideBusinessRepository(): BusinessRepository {
        return BusinessDataRepository()
    }
}
