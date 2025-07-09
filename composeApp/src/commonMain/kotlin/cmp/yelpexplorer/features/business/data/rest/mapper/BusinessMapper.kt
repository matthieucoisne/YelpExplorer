package cmp.yelpexplorer.features.business.data.rest.mapper

import cmp.yelpexplorer.core.utils.Const
import cmp.yelpexplorer.features.business.domain.model.Business
import cmp.yelpexplorer.features.business.data.rest.model.BusinessEntity
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char

fun List<BusinessEntity>.toDomainModel(): List<Business> {
    return map { it.toDomainModel() }
}

@OptIn(FormatStringsInDatetimeFormats::class)
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
            if (hours.isNotEmpty()) {
                val timeParser = LocalTime.Format { byUnicodePattern(Const.PATTERN_HOUR_MINUTE) }
                val timeFormatter = LocalTime.Format {
                    amPmHour()
                    char(':')
                    minute()
                    char(' ')
                    amPmMarker(am = "AM", pm = "PM")
                }

                hours[0].open.groupBy {
                    it.day
                }.mapValues {
                    it.value.map { open ->
                        val start = timeParser.parse(open.start).format(timeFormatter).lowercase()
                        val end = timeParser.parse(open.end).format(timeFormatter).lowercase()
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
