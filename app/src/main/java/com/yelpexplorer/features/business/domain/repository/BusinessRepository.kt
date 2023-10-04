package com.yelpexplorer.features.business.domain.repository

import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.domain.model.Business
import kotlinx.coroutines.flow.Flow

// https://developer.android.com/kotlin/coroutines/coroutines-best-practices#coroutines-data-layer
interface BusinessRepository {
    suspend fun getBusinessList(term: String, location: String, sortBy: String, limit: Int): Flow<Resource<List<Business>>>
    suspend fun getBusinessDetailsWithReviews(businessId: String): Flow<Resource<Business>>
}
