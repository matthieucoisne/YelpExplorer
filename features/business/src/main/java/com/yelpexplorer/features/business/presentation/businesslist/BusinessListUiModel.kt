package com.yelpexplorer.features.business.presentation.businesslist

import com.yelpexplorer.features.business.domain.model.Business

data class BusinessListUiModel(
    val businessList: List<BusinessUiModel>
)

data class BusinessUiModel(
    val id: String,
    val name: String,
    val urlImage: String
)

fun List<Business>.toBusinessListUiModel(): BusinessListUiModel {
    return BusinessListUiModel(map { BusinessUiModel(it.id, it.name, it.urlImage) })
}
