package com.yelpexplorer.features.business.presentation.businessdetails

import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.model.Review

data class BusinessDetailsUiModel(
    val id: String,
    val name: String,
    val photoUrl: String,
    val rating: Double,
    val reviewCount: Int,
    val address: String,
    val price: String,
    val categories: String,
    val openingHours: Map<Int, String>,
    val reviews: List<ReviewUiModel>
)

data class ReviewUiModel(
    val userName: String,
    val userImageUrl: String?,
    val text: String,
    val rating: Double,
    val timeCreated: String
)

fun Business.toBusinessDetailsUiModel(): BusinessDetailsUiModel {
    val openingHours = mutableMapOf<Int, String>()
    for (i in 0..6) {
        hours?.get(i)?.let {
            openingHours[i] = it.joinToString(separator = "\n")
        }
    }

    return BusinessDetailsUiModel(
        id = id,
        name = name,
        photoUrl = photoUrl,
        rating = rating,
        reviewCount = reviewCount,
        address = address,
        price = price,
        categories = categories.joinToString(separator = ", "),
        openingHours = openingHours,
        reviews = reviews?.map { it.toReviewUiModel() } ?: emptyList()
    )
}

private fun Review.toReviewUiModel(): ReviewUiModel {
    return ReviewUiModel(
        userName = user.name,
        userImageUrl = user.imageUrl,
        text = text,
        rating = rating.toDouble(),
        timeCreated = timeCreated
    )
}
