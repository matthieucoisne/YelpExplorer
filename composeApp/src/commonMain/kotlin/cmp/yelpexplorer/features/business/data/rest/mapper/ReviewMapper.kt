package cmp.yelpexplorer.features.business.data.rest.mapper

import cmp.yelpexplorer.core.utils.Const
import cmp.yelpexplorer.features.business.domain.model.Review
import cmp.yelpexplorer.features.business.data.rest.model.ReviewEntity
import cmp.yelpexplorer.features.business.domain.model.User
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
fun List<ReviewEntity>.toDomainModel(): List<Review> {
    val dateParser = LocalDateTime.Format { byUnicodePattern(Const.PATTERN_DATE_TIME) }
    val dateFormatter = LocalDate.Format { byUnicodePattern(Const.PATTERN_DATE) }

    return map {
        Review(
            user = User(
                name = it.user.name,
                imageUrl = it.user.imageUrl
            ),
            text = it.text.replace("\\n+".toRegex(), "\n"),
            rating = it.rating,
            timeCreated = dateParser.parse(it.timeCreated).date.format(dateFormatter).lowercase()
        )
    }
}
