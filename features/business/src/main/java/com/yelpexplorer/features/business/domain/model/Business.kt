package com.yelpexplorer.features.business.domain.model

data class Business(
    val id: String,
    val name: String,
    val urlImage: String
) : Comparable<Business> {

    override fun compareTo(other: Business): Int {
        return name.compareTo(other.name)
    }
}
