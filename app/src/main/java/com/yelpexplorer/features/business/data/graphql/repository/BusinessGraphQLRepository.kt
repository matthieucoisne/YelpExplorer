package com.yelpexplorer.features.business.data.graphql.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.yelpexplorer.BusinessDetailsQuery
import com.yelpexplorer.BusinessListQuery
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.data.graphql.mapper.toDomainModel
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class BusinessGraphQLRepository(private val apolloClient: ApolloClient) : BusinessRepository {

    override suspend fun getBusinessList(term: String, location: String, sortBy: String, limit: Int) = flow<Resource<List<Business>>> {
        try {
            emit(Resource.Loading())

            val response = apolloClient.query(
                BusinessListQuery(
                    term = term,
                    location = location,
                    sortBy = sortBy,
                    limit = limit
                )
            ).execute()

            val businessList = response.data?.search?.business?.mapNotNull { it?.toDomainModel() }
            if (businessList != null) {
                emit(Resource.Success(businessList))
            } else {
                Timber.e("ERROR: businessList is null")
                emit(Resource.Error("ERROR: businessList is null", null))
            }
        } catch (e: ApolloException) {
            Timber.e(e)
            emit(Resource.Error("ERROR: ${e.message}", null))
        }
    }

    override suspend fun getBusinessDetailsWithReviews(businessId: String) = flow<Resource<Business>> {
        try {
            emit(Resource.Loading())

            val response = apolloClient.query(
                BusinessDetailsQuery(id = businessId)
            ).execute()

            val businessDetails = response.data?.business?.toDomainModel()
            if (businessDetails != null) {
                emit(Resource.Success(businessDetails))
            } else {
                Timber.e("ERROR: businessDetails is null")
                emit(Resource.Error("ERROR: businessDetails is null", null))
            }
        } catch (e: ApolloException) {
            Timber.e(e)
            emit(Resource.Error("ERROR: ${e.message}", null))
        }
    }
}
