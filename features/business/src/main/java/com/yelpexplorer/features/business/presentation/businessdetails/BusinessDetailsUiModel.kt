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
    val hours: Map<Int, List<String>>,
    val reviews: List<Review>
)

fun Business.toBusinessDetailsUiModel(): BusinessDetailsUiModel {
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
        hours = hours ?: emptyMap(), // TODO
        reviews = reviews ?: emptyList()
    )
}
