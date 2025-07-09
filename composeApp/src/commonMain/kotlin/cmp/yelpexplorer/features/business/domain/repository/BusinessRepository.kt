package cmp.yelpexplorer.features.business.domain.repository

import cmp.yelpexplorer.features.business.domain.model.Business

// https://developer.android.com/kotlin/coroutines/coroutines-best-practices#coroutines-data-layer
interface BusinessRepository {
    suspend fun getBusinessList(term: String, location: String, sortBy: String, limit: Int): Result<List<Business>>
    suspend fun getBusinessDetailsWithReviews(businessId: String): Result<Business>
}
