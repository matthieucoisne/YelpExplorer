package com.yelpexplorer.features.business.domain

import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.features.business.domain.usecase.GetBusinessDetailsUseCase
import com.yelpexplorer.features.business.domain.usecase.GetBusinessListUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BusinessDomainModule {

    @Provides
    @Singleton
    fun provideBusinessDetailsUseCase(businessRepository: BusinessRepository): GetBusinessDetailsUseCase {
        return GetBusinessDetailsUseCase(businessRepository)
    }

    @Provides
    @Singleton
    fun provideBusinessListUseCase(businessRepository: BusinessRepository): GetBusinessListUseCase {
        return GetBusinessListUseCase(businessRepository)
    }
}
