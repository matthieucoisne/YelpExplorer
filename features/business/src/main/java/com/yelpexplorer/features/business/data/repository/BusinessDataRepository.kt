package com.yelpexplorer.features.business.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.yelpexplorer.features.business.data.mapper.toDomainModel
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.features.business.graphql.BusinessDetailsQuery
import com.yelpexplorer.features.business.graphql.BusinessListQuery
import com.yelpexplorer.libraries.core.utils.Resource
import javax.inject.Inject

class BusinessDataRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : BusinessRepository {

    // TODO rate limiter ?

    override suspend fun getBusinessList(term: String, location: String, sortBy: String, limit: Int): LiveData<Resource<List<Business>>> {
        val data = MutableLiveData<Resource<List<Business>>>()
        data.value = Resource.Loading()

        // TODO use coroutines
        apolloClient.query(
            BusinessListQuery(
                term = term,
                location = location,
                sortBy = sortBy,
                limit = limit
            )
        ).enqueue(object: ApolloCall.Callback<BusinessListQuery.Data>() {
            override fun onResponse(response: Response<BusinessListQuery.Data>) {
                val businessList = response.data()?.search?.business?.mapNotNull { it?.toDomainModel() } ?: emptyList()
                data.postValue(Resource.Success(businessList))
            }

            override fun onFailure(e: ApolloException) {
                data.postValue(Resource.Error("ERROR: ${e.message}", null))
            }
        })
        return data
    }

    override suspend fun getBusinessDetails(businessId: String): LiveData<Resource<Business>> {
        val data = MutableLiveData<Resource<Business>>()
        data.value = Resource.Loading()

        // TODO use coroutines
        apolloClient.query(
            BusinessDetailsQuery(
                id = businessId
            )
        ).enqueue(object: ApolloCall.Callback<BusinessDetailsQuery.Data>() {
            override fun onResponse(response: Response<BusinessDetailsQuery.Data>) {
                val businessDetails = response.data()?.business?.toDomainModel()
                if (businessDetails != null) {
                    data.postValue(Resource.Success(businessDetails))
                } else {
                    data.postValue(Resource.Error("ERROR BUSINESS NULL", null))
                }
            }

            override fun onFailure(e: ApolloException) {
                data.postValue(Resource.Error("ERROR: ${e.message}", null))
            }
        })
        return data
    }
}
