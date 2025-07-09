package cmp.yelpexplorer.features.business.data.rest.repository

import cmp.yelpexplorer.features.business.data.rest.datasource.remote.BusinessApi
import cmp.yelpexplorer.features.business.data.rest.mapper.toDomainModel
import cmp.yelpexplorer.features.business.domain.model.Business
import cmp.yelpexplorer.features.business.domain.repository.BusinessRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BusinessRestRepository(
    private val api: BusinessApi,
    private val dispatcher: CoroutineDispatcher,
) : BusinessRepository {

    override suspend fun getBusinessList(
        term: String,
        location: String,
        sortBy: String,
        limit: Int
    ): Result<List<Business>> {
        return try {
            withContext(dispatcher) {
                val response = api.getBusinessList(term, location, sortBy, limit).getOrThrow()
                val businessList = response.businesses.toDomainModel()
                Result.success(businessList)
            }
        } catch (e: Exception) {
//            Timber.e(e)
            Result.failure(e)
        }
    }

    override suspend fun getBusinessDetailsWithReviews(businessId: String): Result<Business> {
        return try {
            withContext(dispatcher) {
                val deferredBusinessDetails = async { api.getBusinessDetails(businessId) }
                val deferredBusinessReviews = async { api.getBusinessReviews(businessId) }
                val businessDetails = deferredBusinessDetails.await().getOrThrow().toDomainModel()
                val reviews = deferredBusinessReviews.await().getOrThrow().reviews.toDomainModel()
                Result.success(businessDetails.copy(reviews = reviews))
            }
        } catch (e: Exception) {
//            Timber.e(e)
            Result.failure(e)
        }
    }
}
