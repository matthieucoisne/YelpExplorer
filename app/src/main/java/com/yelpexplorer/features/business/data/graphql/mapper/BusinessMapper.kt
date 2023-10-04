package com.yelpexplorer.features.business.data.graphql.mapper

import com.yelpexplorer.BusinessDetailsQuery
import com.yelpexplorer.BusinessListQuery
import com.yelpexplorer.core.utils.Const
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.model.Review
import com.yelpexplorer.features.business.domain.model.User
import com.yelpexplorer.fragment.BusinessDetails
import com.yelpexplorer.fragment.BusinessSummary
import java.text.SimpleDateFormat
import java.util.Locale

fun BusinessListQuery.Business.toDomainModel(): Business {
    return mapBusinessFrom(businessSummary)
}

fun BusinessDetailsQuery.Business.toDomainModel(): Business {
    return mapBusinessFrom(businessSummary, businessDetails)
}

private fun mapBusinessFrom(summary: BusinessSummary, details: BusinessDetails? = null): Business {
    val dateParser = SimpleDateFormat(Const.PATTERN_DATE_TIME, Locale.US)
    val dateFormatter = SimpleDateFormat(Const.PATTERN_DATE, Locale.US)
    val timeParser = SimpleDateFormat(Const.PATTERN_HOUR_MINUTE, Locale.US)
    val timeFormatter = SimpleDateFormat(Const.PATTERN_TIME, Locale.US)

    return Business(
        id = summary.id!!,
        name = summary.name!!,
        photoUrl = summary.photos!!.firstNotNullOfOrNull { it } ?: "",
        rating = summary.rating ?: 0.0,
        reviewCount = summary.review_count ?: 0,
        address = summary.location!!.let { "${it.address1!!}, ${it.city!!}" },
        price = summary.price ?: "",
        categories = summary.categories!!.mapNotNull { it?.title },
        hours = details?.hours?.let { hours ->
            if (hours.isNotEmpty()) {
                hours[0]?.open?.groupBy {
                    it!!.day!!
                }?.mapValues {
                    it.value.map { open ->
                        val start = timeFormatter.format(timeParser.parse(open!!.start!!)!!).lowercase(Locale.US)
                        val end = timeFormatter.format(timeParser.parse(open.end!!)!!).lowercase(Locale.US)
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
                timeCreated = dateFormatter.format(dateParser.parse(it.time_created!!)!!).lowercase(Locale.US)
            )
        }
    )
}
