package cmp.yelpexplorer.core.utils

import yelpexplorer_cmp.composeapp.generated.resources.Res
import yelpexplorer_cmp.composeapp.generated.resources.friday
import yelpexplorer_cmp.composeapp.generated.resources.monday
import yelpexplorer_cmp.composeapp.generated.resources.saturday
import yelpexplorer_cmp.composeapp.generated.resources.sunday
import yelpexplorer_cmp.composeapp.generated.resources.thursday
import yelpexplorer_cmp.composeapp.generated.resources.tuesday
import yelpexplorer_cmp.composeapp.generated.resources.wednesday

object Const {
    // Change the data source for the one you want to use. Choose between REST or GRAPHQL
    val DATASOURCE = DataSource.GRAPHQL

    const val DISPATCHER_DEFAULT = "DISPATCHER_DEFAULT"
    const val DISPATCHER_IO = "DISPATCHER_IO"
    const val DISPATCHER_MAIN = "DISPATCHER_MAIN"

    const val URL_GRAPHQL = "https://api.yelp.com/v3/graphql"
    const val URL_REST = "https://api.yelp.com/v3/"

    // TODO: Add your API KEY - https://docs.developer.yelp.com/docs/fusion-authentication
    const val API_KEY = "YOUR_API_KEY"

    const val PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
    const val PATTERN_DATE = "M/d/yyyy"
    const val PATTERN_HOUR_MINUTE = "HHmm"

    val DAYS = mapOf(
        0 to Res.string.monday,
        1 to Res.string.tuesday,
        2 to Res.string.wednesday,
        3 to Res.string.thursday,
        4 to Res.string.friday,
        5 to Res.string.saturday,
        6 to Res.string.sunday,
    )

    // DEBUG
    const val BEHAVIOR_DELAY_MILLIS: Long = 0
}

enum class DataSource {
    REST,
    GRAPHQL,
}
