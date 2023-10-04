package com.yelpexplorer.features.business.businessdetails

import androidx.compose.material3.ExperimentalMaterial3Api
import com.yelpexplorer.R
import com.yelpexplorer.core.theme.YelpExplorerTheme
import com.yelpexplorer.features.business.BusinessScreenTest
import com.yelpexplorer.features.business.presentation.businessdetails.BusinessDetailsContent
import com.yelpexplorer.features.business.presentation.businessdetails.BusinessDetailsViewModel
import com.yelpexplorer.utils.TestUtils
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class BusinessDetailsScreenTest : BusinessScreenTest() {

    @Test
    fun businessDetails_showLoading() {
        composeTestRule.setContent {
            YelpExplorerTheme {
                BusinessDetailsContent(
                    viewState = BusinessDetailsViewModel.ViewState.ShowLoading,
                    onBackPressed = {}
                )
            }
        }

        businessDetailsScreenRobot {
            TestUtils.waitFor(1_000)
            verifyLoadingIsShown()
        }
    }

    @Test
    fun businessDetails_showError() {
        composeTestRule.setContent {
            YelpExplorerTheme {
                BusinessDetailsContent(
                    viewState = BusinessDetailsViewModel.ViewState.ShowError(R.string.error_something_went_wrong),
                    onBackPressed = {}
                )
            }
        }

        businessDetailsScreenRobot {
            TestUtils.waitFor(1_000)
            verifyErrorIsShown()
        }
    }
}
