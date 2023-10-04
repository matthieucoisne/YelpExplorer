package com.yelpexplorer.features.business.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yelpexplorer.core.navigation.Routes
import com.yelpexplorer.features.business.presentation.businessdetails.BusinessDetailsScreen
import com.yelpexplorer.features.business.presentation.businesslist.BusinessListScreen
import com.yelpexplorer.core.theme.YelpExplorerTheme

class BusinessActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YelpExplorerTheme(darkTheme = true) {
                YelpExplorerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YelpExplorerApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.BusinessList.route,
    ) {
        composable(
            route = Routes.BusinessList.route
        ) {
            BusinessListScreen(
                modifier = Modifier.fillMaxSize(),
                onBusinessClicked = { businessId ->
                    navController.navigate(
                        route = Routes.BusinessDetails.createRoute(businessId)
                    )
                }
            )
        }

        composable(
            route = Routes.BusinessDetails.route,
            arguments = Routes.BusinessDetails.navArguments
        ) {
            BusinessDetailsScreen(
                modifier = Modifier.fillMaxSize(),
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}
