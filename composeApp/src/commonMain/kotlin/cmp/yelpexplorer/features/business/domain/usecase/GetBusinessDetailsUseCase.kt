package cmp.yelpexplorer.features.business.domain.usecase

import cmp.yelpexplorer.features.business.domain.model.Business
import cmp.yelpexplorer.features.business.domain.repository.BusinessRepository

interface BusinessDetailsUseCase {
    suspend fun execute(businessId: String): Result<Business>
}

class GetBusinessDetailsUseCase(private val businessRepository: BusinessRepository): BusinessDetailsUseCase {
    override suspend fun execute(businessId: String): Result<Business> {
        return businessRepository.getBusinessDetailsWithReviews(businessId)
    }
}
