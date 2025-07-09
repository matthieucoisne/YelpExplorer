package cmp.yelpexplorer.features.business.data.graphql.mapper

import cmp.yelpexplorer.BusinessDetailsQuery
import cmp.yelpexplorer.BusinessListQuery
import cmp.yelpexplorer.core.utils.Const
import cmp.yelpexplorer.features.business.domain.model.Business
import cmp.yelpexplorer.features.business.domain.model.Review
import cmp.yelpexplorer.features.business.domain.model.User
import cmp.yelpexplorer.fragment.BusinessDetails
import cmp.yelpexplorer.fragment.BusinessSummary
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char

fun BusinessListQuery.Business.toDomainModel(): Business {
    return mapBusinessFrom(businessSummary)
}

fun BusinessDetailsQuery.Business.toDomainModel(): Business {
    return mapBusinessFrom(businessSummary, businessDetails)
}

@OptIn(FormatStringsInDatetimeFormats::class)
private fun mapBusinessFrom(summary: BusinessSummary, details: BusinessDetails? = null): Business {
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
                val timeParser = LocalTime.Format { byUnicodePattern(Const.PATTERN_HOUR_MINUTE) }
                val timeFormatter = LocalTime.Format {
                    amPmHour()
                    char(':')
                    minute()
                    char(' ')
                    amPmMarker(am = "AM", pm = "PM")
                }
                hours[0]?.open?.groupBy {
                    it!!.day!!
                }?.mapValues {
                    it.value.map { open ->
                        val start = timeFormatter.format(timeParser.parse(open!!.start!!)).lowercase()
                        val end = timeFormatter.format(timeParser.parse(open.end!!)).lowercase()
                        "$start - $end"
                    }
                }
            } else {
                emptyMap()
            }
        },
        reviews = details?.reviews?.let { reviews ->
            val dateParser = LocalDateTime.Format { byUnicodePattern(Const.PATTERN_DATE_TIME) }
            val dateFormatter = LocalDate.Format { byUnicodePattern(Const.PATTERN_DATE) }
            reviews.map {
                val user = it!!.user!!
                Review(
                    user = User(user.name!!, user.image_url),
                    text = it.text!!,
                    rating = it.rating!!,
                    timeCreated = dateParser.parse(it.time_created!!).date.format(dateFormatter).lowercase()
                )
            }
        }
    )
}
