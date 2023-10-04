package com.yelpexplorer.core.utils

import com.yelpexplorer.R

object Const {
    // Change the data source for the one you want to use. Choose between REST or GRAPHQL
    val DATASOURCE = DataSource.GRAPHQL

    const val URL_GRAPHQL = "https://api.yelp.com/v3/graphql"
    const val URL_REST = "https://api.yelp.com/v3/"

    // TODO: Add your API KEY - https://docs.developer.yelp.com/docs/fusion-authentication
    const val API_KEY = "YOUR_API_KEY"

    const val PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
    const val PATTERN_DATE = "M/d/yyyy"
    const val PATTERN_HOUR_MINUTE = "HHmm"
    const val PATTERN_TIME = "h:mm a"

    val DAYS = mapOf(
        0 to R.string.monday,
        1 to R.string.tuesday,
        2 to R.string.wednesday,
        3 to R.string.thursday,
        4 to R.string.friday,
        5 to R.string.saturday,
        6 to R.string.sunday,
    )

    // DEBUG
    const val BEHAVIOR_DELAY_MILLIS: Long = 0
}

enum class DataSource {
    REST,
    GRAPHQL,
}