package com.yelpexplorer.features.business.domain.usecase

import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BusinessDetailsUseCase {
    suspend fun execute(businessId: String): Flow<Resource<Business>>
}

class GetBusinessDetailsUseCase(private val businessRepository: BusinessRepository): BusinessDetailsUseCase {
    override suspend fun execute(businessId: String): Flow<Resource<Business>> {
        return businessRepository.getBusinessDetailsWithReviews(businessId)
    }
}
