package com.yelpexplorer.features.business.businessdetails

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.yelpexplorer.R
import com.yelpexplorer.features.business.BusinessScreenTest

internal fun BusinessScreenTest.businessDetailsScreenRobot(
    func: BusinessDetailsScreenRobot.() -> Unit
) = BusinessDetailsScreenRobot(composeTestRule).run { func() }

internal class BusinessDetailsScreenRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
){
    fun scrollToNodeWithText(text: String) {
        composeTestRule.onNodeWithText(text).performScrollTo()
    }

    fun verifyDetailsAreShown(text: String) {
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    fun verifyLoadingIsShown() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.loading)).assertIsDisplayed()
    }

    fun verifyErrorIsShown() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.error_something_went_wrong)).assertIsDisplayed()
    }
}
