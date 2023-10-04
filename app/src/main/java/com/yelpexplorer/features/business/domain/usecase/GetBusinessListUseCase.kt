package com.yelpexplorer.features.business.domain.usecase

import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BusinessListUseCase {
    suspend fun execute(term: String, location: String, sortBy: String, limit: Int): Flow<Resource<List<Business>>>
}

class GetBusinessListUseCase(private val businessRepository: BusinessRepository) : BusinessListUseCase {
    override suspend fun execute(term: String, location: String, sortBy: String, limit: Int): Flow<Resource<List<Business>>> {
        return businessRepository.getBusinessList(term, location, sortBy, limit)
    }
}
