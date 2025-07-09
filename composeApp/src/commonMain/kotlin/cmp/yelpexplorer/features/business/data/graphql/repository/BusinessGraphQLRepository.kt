package cmp.yelpexplorer.features.business.data.graphql.repository

import com.apollographql.apollo.ApolloClient
import cmp.yelpexplorer.BusinessDetailsQuery
import cmp.yelpexplorer.BusinessListQuery
import cmp.yelpexplorer.features.business.data.graphql.mapper.toDomainModel
import cmp.yelpexplorer.features.business.domain.model.Business
import cmp.yelpexplorer.features.business.domain.repository.BusinessRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BusinessGraphQLRepository(
    private val apolloClient: ApolloClient,
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
                val response = apolloClient.query(
                    query = BusinessListQuery(
                        term = term,
                        location = location,
                        sortBy = sortBy,
                        limit = limit
                    )
                ).execute()

                if (response.exception != null) {
                    throw Exception("ERROR: ${response.exception!!.message}")
                } else {
                    val businessList = response.data?.search?.business?.mapNotNull { it?.toDomainModel() }
                    if (businessList == null) {
//                        Timber.e("ERROR: businessList is null")
                        throw Exception("ERROR: businessList is null")
                    } else {
                        Result.success(value = businessList)
                    }
                }
            }
        } catch (e: Exception) {
//            Timber.e(e)
            Result.failure(e)
        }
    }

    override suspend fun getBusinessDetailsWithReviews(businessId: String): Result<Business> {
        return try {
            withContext(dispatcher) {
                val response = apolloClient.query(
                    query = BusinessDetailsQuery(
                        id = businessId
                    )
                ).execute()

                if (response.exception != null) {
                    throw Exception("ERROR: ${response.exception!!.message}")
                } else {
                    val businessDetails = response.data?.business?.toDomainModel()
                    if (businessDetails == null) {
//                        Timber.e("ERROR: businessDetails is null")
                        throw Exception("ERROR: businessDetails is null")
                    } else {
                        Result.success(businessDetails)
                    }
                }
            }
        } catch (e: Exception) {
//            Timber.e(e)
            Result.failure(e)
        }
    }
}
