package com.yelpexplorer.features.business.domain.model

data class Business(
    val id: String,
    val name: String,
    val photoUrls: List<String>,
    val rating: Double,
    val reviewCount: Int,
    val address: String,
    val price: String,
    val categories: List<String>,
    val phone: String? = null,
    val hours: Map<Int, List<String>>? = null,
    val reviews: List<Review>? = null
)
