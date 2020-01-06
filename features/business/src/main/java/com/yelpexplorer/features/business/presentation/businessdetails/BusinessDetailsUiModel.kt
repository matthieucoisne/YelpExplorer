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
    val phone: String,
    val openingHours: Map<Int, String>,
    val reviews: List<Review>
)

fun Business.toBusinessDetailsUiModel(): BusinessDetailsUiModel {
    val openingHours = mutableMapOf<Int, String>()
    for (i in 0..6) {
        hours!![i]?.let {
            openingHours[i] = it.joinToString(separator = "\n")
        }
    }

    return BusinessDetailsUiModel(
        id = id,
        name = name,
        photoUrl = photoUrls.firstOrNull() ?: "",
        rating = rating,
        reviewCount = reviewCount,
        address = address,
        price = price,
        categories = categories.joinToString(separator = ", "),
        phone = phone ?: "",
        openingHours = openingHours,
        reviews = reviews ?: emptyList()
    )
}
