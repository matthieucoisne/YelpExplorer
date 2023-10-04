package com.yelpexplorer.features.business.data.rest.mapper

import com.yelpexplorer.core.utils.Const
import com.yelpexplorer.features.business.domain.model.Review
import com.yelpexplorer.features.business.data.rest.model.ReviewEntity
import com.yelpexplorer.features.business.domain.model.User
import java.text.SimpleDateFormat
import java.util.Locale

fun List<ReviewEntity>.toDomainModel(): List<Review> {
    val dateParser = SimpleDateFormat(Const.PATTERN_DATE_TIME, Locale.US)
    val dateFormatter = SimpleDateFormat(Const.PATTERN_DATE, Locale.US)

    return map {
        Review(
            user = User(
                name = it.user.name,
                imageUrl = it.user.imageUrl
            ),
            text = it.text.replace("\\n+".toRegex(), "\n"),
            rating = it.rating,
            timeCreated = dateFormatter.format(dateParser.parse(it.timeCreated)!!).lowercase()
        )
    }
}
