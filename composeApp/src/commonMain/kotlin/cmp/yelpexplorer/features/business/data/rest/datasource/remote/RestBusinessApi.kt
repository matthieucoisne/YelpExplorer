package cmp.yelpexplorer.features.business.data.rest.datasource.remote

import cmp.yelpexplorer.features.business.data.rest.model.BusinessEntity
import cmp.yelpexplorer.features.business.data.rest.model.BusinessListResponse
import cmp.yelpexplorer.features.business.data.rest.model.ReviewListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * https://api.yelp.com/v3/businesses/search?term=sushi&location=montreal&sortBy=rating&limit=20
 * https://api.yelp.com/v3/businesses/FI3PVYBuz5fioko7qhsPZA
 * https://api.yelp.com/v3/businesses/FI3PVYBuz5fioko7qhsPZA/reviews
 */
class RestBusinessApi(private val httpClient: HttpClient) : BusinessApi {
    override suspend fun getBusinessList(
        term: String,
        location: String,
        sortBy: String,
        limit: Int
    ): Result<BusinessListResponse> = runCatching {
        httpClient.get("businesses/search") {
            parameter("term", term)
            parameter("location", location)
            parameter("sortBy", sortBy)
            parameter("limit", "$limit")
        }.body()
    }

    override suspend fun getBusinessDetails(businessId: String): Result<BusinessEntity> = runCatching {
        httpClient.get("businesses/$businessId").body()
    }

    override suspend fun getBusinessReviews(businessId: String): Result<ReviewListResponse> = runCatching {
        httpClient.get("businesses/$businessId/reviews").body()
    }
}
