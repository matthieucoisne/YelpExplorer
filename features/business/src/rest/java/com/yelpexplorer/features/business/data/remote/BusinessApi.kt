package com.yelpexplorer.features.business.data.remote

import com.yelpexplorer.features.business.data.model.BusinessEntity
import com.yelpexplorer.features.business.data.model.BusinessListResponse
import com.yelpexplorer.features.business.data.model.ReviewListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * https://api.yelp.com/v3/businesses/search?term=sushi&location=montreal&sortBy=rating&limit=10
 * https://api.yelp.com/v3/businesses/FI3PVYBuz5fioko7qhsPZA
 * https://api.yelp.com/v3/businesses/FI3PVYBuz5fioko7qhsPZA/reviews
 */
interface BusinessApi {

    @GET("businesses/search")
    suspend fun getBusinessList(
        @Query("term") term: String,
        @Query("location") location: String,
        @Query("sortBy") sortBy: String,
        @Query("limit") limit: Int
    ): BusinessListResponse

    @GET("businesses/{businessId}")
    suspend fun getBusinessDetails(
        @Path("businessId") businessId: String
    ): BusinessEntity

    @GET("businesses/{businessId}/reviews")
    suspend fun getBusinessReviews(
        @Path("businessId") businessId: String
    ): ReviewListResponse
}
