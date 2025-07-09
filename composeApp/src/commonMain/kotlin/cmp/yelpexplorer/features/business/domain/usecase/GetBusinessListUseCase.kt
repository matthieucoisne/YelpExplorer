package cmp.yelpexplorer.features.business.domain.usecase

import cmp.yelpexplorer.features.business.domain.model.Business
import cmp.yelpexplorer.features.business.domain.repository.BusinessRepository

interface BusinessListUseCase {
    suspend fun execute(term: String, location: String, sortBy: String, limit: Int): Result<List<Business>>
}

class GetBusinessListUseCase(private val businessRepository: BusinessRepository) : BusinessListUseCase {
    override suspend fun execute(term: String, location: String, sortBy: String, limit: Int): Result<List<Business>> {
        return businessRepository.getBusinessList(term, location, sortBy, limit)
    }
}
