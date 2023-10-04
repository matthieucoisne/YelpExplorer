package com.yelpexplorer.features.business.businesslist

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.yelpexplorer.R
import com.yelpexplorer.core.navigation.Routes
import com.yelpexplorer.core.theme.YelpExplorerTheme
import com.yelpexplorer.features.business.BusinessScreenTest
import com.yelpexplorer.features.business.businessdetails.businessDetailsScreenRobot
import com.yelpexplorer.features.business.presentation.YelpExplorerApp
import com.yelpexplorer.features.business.presentation.businesslist.BusinessListContent
import com.yelpexplorer.features.business.presentation.businesslist.BusinessListViewModel
import com.yelpexplorer.runner.MockTestRunner
import com.yelpexplorer.utils.TestUtils
import com.yelpexplorer.utils.TestUtils.Companion.assertCurrentRouteName
import com.yelpexplorer.utils.TestUtils.Companion.enqueueMockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class BusinessListScreenTest: BusinessScreenTest() {

    private lateinit var navController: TestNavHostController
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun happyPath_using_MockWebServer() {
        mockWebServer = MockWebServer()
        mockWebServer.enqueueMockResponse(filePath = "responses/getBusinessList.json")
        mockWebServer.enqueueMockResponse(filePath = "responses/getBusinessDetails.json")
        mockWebServer.enqueueMockResponse(filePath = "responses/getBusinessReviews.json")
        mockWebServer.start(MockTestRunner.MOCK_WEB_SERVER_PORT)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            YelpExplorerTheme(darkTheme = true) {
                YelpExplorerApp(navController)
            }
        }

        val businessName = "1. JUN I"
        businessListScreenRobot {
            navController.assertCurrentRouteName(Routes.BusinessList.route)
            verifyBusinessIsDisplayed(businessName)
            TestUtils.waitFor(1_000)
            clickBusinessWithName(businessName)
        }

        val userName = "Will S."
        businessDetailsScreenRobot {
            navController.assertCurrentRouteName(Routes.BusinessDetails.route)
            scrollToNodeWithText(userName)
            TestUtils.waitFor(1_000)
            verifyDetailsAreShown(userName)
        }

        // TODO back press?

        mockWebServer.shutdown()
    }

    @Test
    fun businessList_showLoading() {
        composeTestRule.setContent {
            YelpExplorerTheme {
                BusinessListContent(
                    viewState = BusinessListViewModel.ViewState.ShowLoading,
                    onBusinessClicked = {}
                )
            }
        }

        businessListScreenRobot {
            TestUtils.waitFor(1_000)
            verifyLoadingIsShown()
        }
    }

    @Test
    fun businessList_showError() {
        composeTestRule.setContent {
            YelpExplorerTheme {
                BusinessListContent(
                    viewState = BusinessListViewModel.ViewState.ShowError(R.string.error_something_went_wrong),
                    onBusinessClicked = {}
                )
            }
        }

        businessListScreenRobot {
            TestUtils.waitFor(1_000)
            verifyErrorIsShown()
        }
    }
}
