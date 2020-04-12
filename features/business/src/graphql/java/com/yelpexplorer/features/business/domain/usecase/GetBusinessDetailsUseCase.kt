package com.yelpexplorer.features.business.domain.usecase

import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.libraries.core.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetBusinessDetailsUseCase(
    private val businessRepository: BusinessRepository
) {

    suspend fun execute(businessId: String): Flow<Resource<Business>> {
        return businessRepository.getBusinessDetails(businessId = businessId)
    }
}
