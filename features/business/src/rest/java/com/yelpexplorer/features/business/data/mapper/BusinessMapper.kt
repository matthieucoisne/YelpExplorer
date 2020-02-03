package com.yelpexplorer.features.business.data.mapper

import com.yelpexplorer.features.business.data.model.BusinessEntity
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.libraries.core.data.local.Const
import java.text.SimpleDateFormat
import java.util.Locale

fun List<BusinessEntity>.toDomainModel(): List<Business> {
    return map { it.toDomainModel() }
}

fun BusinessEntity.toDomainModel(): Business {
    val timeParser = SimpleDateFormat(Const.PATTERN_HOUR_MINUTE, Locale.US)
    val timeFormatter = SimpleDateFormat(Const.PATTERN_TIME, Locale.US)

    return Business(
        id = id,
        name = name,
        photoUrl = image_url,
        rating = rating ?: 0.0,
        reviewCount = reviewCount ?: 0,
        address = "${location.address1}, ${location.city}",
        price = price ?: "",
        categories = categories.mapNotNull{ it?.title },
        phone = display_phone,
        hours = hours?.let { hours ->
            if (hours.isNotEmpty()) {
                hours[0].open.groupBy {
                    it.day
                }.mapValues {
                    it.value.map { open ->
                        val start = timeFormatter.format(timeParser.parse(open.start)!!).toLowerCase(Locale.US)
                        val end = timeFormatter.format(timeParser.parse(open.end)!!).toLowerCase(Locale.US)
                        "$start - $end"
                    }
                }
            } else {
                emptyMap()
            }
        },
        reviews = null
    )
}
