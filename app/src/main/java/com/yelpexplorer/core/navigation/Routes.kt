package com.yelpexplorer.core.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Routes(val route: String) {
    object BusinessList : Routes("BusinessList")

    object BusinessDetails : Routes("BusinessDetails/{businessId}") {
        fun createRoute(businessId: String) = "BusinessDetails/$businessId"
        val navArguments = listOf(
            navArgument("businessId") { type = NavType.StringType }
        )
    }
}
