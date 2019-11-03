package com.yelpexplorer.features.business.presentation.businessdetails

import com.yelpexplorer.features.business.domain.model.Business

data class BusinessDetailsUiModel(
    val id: String,
    val name: String,
    val urlImage: String
)

fun Business.toBusinessDetailsUiModel(): BusinessDetailsUiModel {
    return BusinessDetailsUiModel(id, name, urlImage)
}
