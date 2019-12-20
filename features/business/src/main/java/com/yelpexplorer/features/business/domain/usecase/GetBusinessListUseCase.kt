package com.yelpexplorer.features.business.domain.usecase

import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.libraries.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBusinessListUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
) {

    suspend fun execute(term: String, location: String, sortBy: String, limit: Int): Flow<Resource<List<Business>>> {
        return businessRepository.getBusinessList(
            term = term,
            location = location,
            sortBy = sortBy,
            limit = limit
        )
    }
}
