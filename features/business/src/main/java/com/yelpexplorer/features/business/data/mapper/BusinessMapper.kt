package com.yelpexplorer.features.business.data.mapper

import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.model.Review
import com.yelpexplorer.features.business.domain.model.User
import com.yelpexplorer.features.business.graphql.BusinessDetailsQuery
import com.yelpexplorer.features.business.graphql.BusinessListQuery
import com.yelpexplorer.features.business.graphql.fragment.BusinessDetails
import com.yelpexplorer.features.business.graphql.fragment.BusinessSummary

fun BusinessListQuery.Business.toDomainModel(): Business {
    return mapBusinessFrom(fragments.businessSummary)
}

fun BusinessDetailsQuery.Business.toDomainModel(): Business {
    return mapBusinessFrom(fragments.businessSummary, fragments.businessDetails)
}

private fun mapBusinessFrom(summary: BusinessSummary, details: BusinessDetails? = null): Business {
    return Business(
        id = summary.id!!,
        name = summary.name!!,
        photoUrls = summary.photos!!.mapNotNull { it },
        rating = summary.rating ?: 0.0,
        reviewCount = summary.review_count ?: 0,
        address = summary.location!!.let { "${it.address1!!}, ${it.city!!}" },
        price = summary.price ?: "",
        categories = summary.categories!!.map { it!!.title!! },
        phone = details?.display_phone,
        hours = details?.hours?.get(0)?.open?.groupBy {
            it!!.day!!
        }?.mapValues {
            it.value.map { open ->
                "${open!!.start!!} - ${open.end!!}"
            }
        },
        reviews = details?.reviews?.map {
            val user = it!!.user!!
            Review(
                user = User(user.name!!, user.image_url),
                text = it.text!!,
                rating = it.rating!!,
                timeCreated = it.time_created!!
            )
        }
    )
}
