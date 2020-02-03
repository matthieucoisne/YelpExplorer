package com.yelpexplorer.features.business.data.mapper

import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.model.Review
import com.yelpexplorer.features.business.domain.model.User
import com.yelpexplorer.features.business.graphql.BusinessDetailsQuery
import com.yelpexplorer.features.business.graphql.BusinessListQuery
import com.yelpexplorer.features.business.graphql.fragment.BusinessDetails
import com.yelpexplorer.features.business.graphql.fragment.BusinessSummary
import com.yelpexplorer.libraries.core.data.local.Const
import java.text.SimpleDateFormat
import java.util.Locale

fun BusinessListQuery.Business.toDomainModel(): Business {
    return mapBusinessFrom(fragments.businessSummary)
}

fun BusinessDetailsQuery.Business.toDomainModel(): Business {
    return mapBusinessFrom(fragments.businessSummary, fragments.businessDetails)
}

private fun mapBusinessFrom(summary: BusinessSummary, details: BusinessDetails? = null): Business {
    val dateParser = SimpleDateFormat(Const.PATTERN_DATE_TIME, Locale.US)
    val dateFormatter = SimpleDateFormat(Const.PATTERN_DATE, Locale.US)
    val timeParser = SimpleDateFormat(Const.PATTERN_HOUR_MINUTE, Locale.US)
    val timeFormatter = SimpleDateFormat(Const.PATTERN_TIME, Locale.US)

    return Business(
        id = summary.id!!,
        name = summary.name!!,
        photoUrl = summary.photos!!.mapNotNull { it }.firstOrNull() ?: "",
        rating = summary.rating ?: 0.0,
        reviewCount = summary.review_count ?: 0,
        address = summary.location!!.let { "${it.address1!!}, ${it.city!!}" },
        price = summary.price ?: "",
        categories = summary.categories!!.mapNotNull { it?.title },
        phone = details?.display_phone,
        hours = details?.hours?.let { hours ->
            if (hours.isNotEmpty()) {
                hours[0]?.open?.groupBy {
                    it!!.day!!
                }?.mapValues {
                    it.value.map { open ->
                        val start = timeFormatter.format(timeParser.parse(open!!.start!!)!!).toLowerCase(Locale.US)
                        val end = timeFormatter.format(timeParser.parse(open.end!!)!!).toLowerCase(Locale.US)
                        "$start - $end"
                    }
                }
            } else {
                emptyMap()
            }
        },
        reviews = details?.reviews?.map {
            val user = it!!.user!!
            Review(
                user = User(user.name!!, user.image_url),
                text = it.text!!,
                rating = it.rating!!,
                timeCreated = dateFormatter.format(dateParser.parse(it.time_created!!)!!).toLowerCase(Locale.US)
            )
        }
    )
}
