package com.yelpexplorer.features.business.data.model

data class ReviewListResponse(
    val reviews: List<ReviewEntity>
)

data class ReviewsEntity(
    val reviews: List<ReviewEntity>
)

data class ReviewEntity(
    val user: UserEntity,
    val text: String,
    val rating: Int,
    val time_created: String
)
