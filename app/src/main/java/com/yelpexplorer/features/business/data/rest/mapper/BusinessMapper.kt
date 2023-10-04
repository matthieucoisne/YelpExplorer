package com.yelpexplorer.features.business.data.rest.mapper

import com.yelpexplorer.core.utils.Const
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.data.rest.model.BusinessEntity
import java.text.SimpleDateFormat
import java.util.Locale

fun List<BusinessEntity>.toDomainModel(): List<Business> {
    return map { it.toDomainModel() }
}

fun BusinessEntity.toDomainModel(): Business {
    return Business(
        id = id,
        name = name,
        photoUrl = imageUrl,
        rating = rating ?: 0.0,
        reviewCount = reviewCount ?: 0,
        address = "${location.address1}, ${location.city}",
        price = price ?: "",
        categories = categories.mapNotNull{ it?.title },
        hours = hours?.let { hours ->
            val timeParser = SimpleDateFormat(Const.PATTERN_HOUR_MINUTE, Locale.US)
            val timeFormatter = SimpleDateFormat(Const.PATTERN_TIME, Locale.US)
            if (hours.isNotEmpty()) {
                hours[0].open.groupBy {
                    it.day
                }.mapValues {
                    it.value.map { open ->
                        val start = timeFormatter.format(timeParser.parse(open.start)!!).lowercase(Locale.US)
                        val end = timeFormatter.format(timeParser.parse(open.end)!!).lowercase(Locale.US)
                        "$start - $end"
                    }
                }
            } else {
                null
            }
        },
        reviews = null
    )
}
