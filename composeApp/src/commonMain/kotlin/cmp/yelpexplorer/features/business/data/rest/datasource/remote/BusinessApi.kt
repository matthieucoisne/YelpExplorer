package cmp.yelpexplorer.features.business.data.rest.datasource.remote

import cmp.yelpexplorer.features.business.data.rest.model.BusinessEntity
import cmp.yelpexplorer.features.business.data.rest.model.BusinessListResponse
import cmp.yelpexplorer.features.business.data.rest.model.ReviewListResponse

/**
 * https://api.yelp.com/v3/businesses/search?term=sushi&location=montreal&sortBy=rating&limit=20
 * https://api.yelp.com/v3/businesses/FI3PVYBuz5fioko7qhsPZA
 * https://api.yelp.com/v3/businesses/FI3PVYBuz5fioko7qhsPZA/reviews
 */
interface BusinessApi {
    suspend fun getBusinessList(
        term: String,
        location: String,
        sortBy: String,
        limit: Int
    ): Result<BusinessListResponse>

    suspend fun getBusinessDetails(
        businessId: String
    ): Result<BusinessEntity>

    suspend fun getBusinessReviews(
        businessId: String
    ): Result<ReviewListResponse>
}
