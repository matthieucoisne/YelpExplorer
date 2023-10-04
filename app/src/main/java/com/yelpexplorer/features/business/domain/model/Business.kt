package com.yelpexplorer.features.business.domain.model

data class Business(
    val id: String,
    val name: String,
    val photoUrl: String,
    val rating: Double,
    val reviewCount: Int,
    val address: String,
    val price: String,
    val categories: List<String>,
    val hours: Map<Int, List<String>>?,
    val reviews: List<Review>?,
)
