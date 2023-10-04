package com.yelpexplorer.features.business.data.rest.repository

import com.yelpexplorer.features.business.data.rest.mapper.toDomainModel
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.data.rest.datasource.remote.BusinessApi
import com.yelpexplorer.features.business.domain.model.Business
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class BusinessRestRepository(private val api: BusinessApi) : BusinessRepository {

    override suspend fun getBusinessList(
        term: String,
        location: String,
        sortBy: String,
        limit: Int
    ) = flow<Resource<List<Business>>> {
        try {
            emit(Resource.Loading())
            val response = api.getBusinessList(term, location, sortBy, limit)
            val businessList = response.businesses.toDomainModel()
            emit(Resource.Success(businessList))
        } catch (e: Exception) {
            Timber.e(e)
            emit(Resource.Error("ERROR: ${e.message}", null))
        }
    }

    override suspend fun getBusinessDetailsWithReviews(businessId: String) = flow<Resource<Business>> {
        try {
            coroutineScope {
                emit(Resource.Loading())
                val deferredBusinessDetails = async { api.getBusinessDetails(businessId) }
                val deferredBusinessReviews = async { api.getBusinessReviews(businessId) }
                val businessDetails = deferredBusinessDetails.await().toDomainModel()
                val reviews = deferredBusinessReviews.await().reviews.toDomainModel()
                emit(Resource.Success(businessDetails.copy(reviews = reviews)))
            }
        } catch (e: Exception) {
            Timber.e(e)
            emit(Resource.Error("ERROR: ${e.message}", null))
        }
    }
}
