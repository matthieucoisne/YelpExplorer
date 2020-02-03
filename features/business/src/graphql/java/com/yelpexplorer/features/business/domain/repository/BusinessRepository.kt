package com.yelpexplorer.features.business.domain.repository

import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.libraries.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BusinessRepository {
    suspend fun getBusinessList(term: String, location: String, sortBy: String, limit: Int): Flow<Resource<List<Business>>>
    suspend fun getBusinessDetails(businessId: String): Flow<Resource<Business>>
}
