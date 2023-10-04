package com.yelpexplorer.features.business.presentation.businesslist

import com.yelpexplorer.features.business.domain.model.Business

data class BusinessListUiModel(
    val businessList: List<BusinessUiModel>
)

data class BusinessUiModel(
    val id: String,
    val name: String,
    val photoUrl: String,
    val rating: Double,
    val reviewCount: Int,
    val address: String,
    val price: String,
    val categories: String,
)

fun List<Business>.toBusinessListUiModel(): BusinessListUiModel {
    return BusinessListUiModel(
        businessList = map {
            BusinessUiModel(
                id = it.id,
                name = it.name,
                photoUrl = it.photoUrl,
                rating = it.rating,
                reviewCount = it.reviewCount,
                address = it.address,
                price = it.price,
                categories = it.categories.joinToString(separator = ", ")
            )
        }
    )
}
