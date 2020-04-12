package com.yelpexplorer.features.business.data.repository

import com.yelpexplorer.features.business.data.mapper.toDomainModel
import com.yelpexplorer.features.business.data.remote.BusinessApi
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.model.Review
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.libraries.core.utils.Resource
import kotlinx.coroutines.flow.flow

class BusinessDataRepository(
    private val api: BusinessApi
) : BusinessRepository {

    override suspend fun getBusinessList(term: String, location: String, sortBy: String, limit: Int) = flow<Resource<List<Business>>> {
        try {
            emit(Resource.Loading())

            val response = api.getBusinessList(
                term = term,
                location = location,
                sortBy = sortBy,
                limit = limit
            )

            val businessList = response.businesses.toDomainModel()
            emit(Resource.Success(businessList))
        } catch (e: Exception) {
            emit(Resource.Error("ERROR: ${e.message}", null))
        }
    }

    override suspend fun getBusinessDetails(businessId: String) = flow<Resource<Business>> {
        try {
            emit(Resource.Loading())

            val response = api.getBusinessDetails(
                businessId = businessId
            )

            val businessDetails = response.toDomainModel()
            emit(Resource.Success(businessDetails))
        } catch (e: Exception) {
            emit(Resource.Error("ERROR: ${e.message}", null))
        }
    }

    override suspend fun getBusinessReviews(businessId: String) = flow<Resource<List<Review>>> {
        try {
            emit(Resource.Loading())

            val response = api.getBusinessReviews(
                businessId = businessId
            )

            val reviews = response.reviews.toDomainModel()
            emit(Resource.Success(reviews))
        } catch (e: Exception) {
            emit(Resource.Error("ERROR: ${e.message}", null))
        }
    }
}
