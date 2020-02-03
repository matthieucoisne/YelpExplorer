package com.yelpexplorer.features.business.data.mapper

import com.yelpexplorer.features.business.data.model.ReviewEntity
import com.yelpexplorer.features.business.domain.model.Review
import com.yelpexplorer.features.business.domain.model.User
import com.yelpexplorer.libraries.core.data.local.Const
import java.text.SimpleDateFormat
import java.util.Locale

fun List<ReviewEntity>.toDomainModel(): List<Review> {
    val dateParser = SimpleDateFormat(Const.PATTERN_DATE_TIME, Locale.US)
    val dateFormatter = SimpleDateFormat(Const.PATTERN_DATE, Locale.US)

    return map {
        Review(
            user = User(
                name = it.user.name,
                imageUrl = it.user.image_url
            ),
            text = it.text,
            rating = it.rating,
            timeCreated = dateFormatter.format(dateParser.parse(it.time_created)!!).toLowerCase(Locale.US)
        )
    }
}
