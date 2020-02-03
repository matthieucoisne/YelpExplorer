package com.yelpexplorer.features.business.data.model

data class BusinessListResponse(
    val businesses: List<BusinessEntity>
)

data class BusinessEntity(
    val id: String,
    val name: String,
    val image_url: String,
    val reviewCount: Int?,
    val categories: List<Category?>,
    val rating: Double?,
    val price: String?,
    val location: Location,
    val display_phone: String? = null,
    val hours: List<HoursEntity>? = null,
    val reviews: ReviewsEntity? = null
) {
    data class Category(
        val title: String
    )

    data class Location(
        val address1: String,
        val city: String
    )

    data class HoursEntity(
        val open: List<Open>
    )

    data class Open(
        val start: String,
        val end: String,
        val day: Int
    )
}
