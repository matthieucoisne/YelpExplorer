package com.yelpexplorer.features.business.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.yelpexplorer.features.business.data.mapper.toDomainModel
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.features.business.graphql.BusinessDetailsQuery
import com.yelpexplorer.features.business.graphql.BusinessListQuery
import com.yelpexplorer.libraries.core.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessDataRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : BusinessRepository {

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
            ).toDeferred().await()

            val businessList = response.data()?.search?.business?.mapNotNull { it?.toDomainModel() } ?: emptyList()
            emit(Resource.Success(businessList))
        } catch (e: ApolloException) {
            emit(Resource.Error("ERROR: ${e.message}", null))
        }
    }

    override suspend fun getBusinessDetails(businessId: String) = flow<Resource<Business>> {
        try {
            emit(Resource.Loading())

            val response = apolloClient.query(
                BusinessDetailsQuery(
                    id = businessId
                )
            ).toDeferred().await()

            val businessDetails = response.data()?.business?.toDomainModel()
            if (businessDetails != null) {
                emit(Resource.Success(businessDetails))
            } else {
                emit(Resource.Error("ERROR BUSINESS NULL", null))
            }
        } catch (e: ApolloException) {
            emit(Resource.Error("ERROR: ${e.message}", null))
        }
    }
}
