package com.yelpexplorer.features.business.businesslist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.yelpexplorer.R
import com.yelpexplorer.features.business.BusinessScreenTest

internal fun BusinessScreenTest.businessListScreenRobot(
    func: BusinessListScreenRobot.() -> Unit
) = BusinessListScreenRobot(composeTestRule).run { func() }

internal class BusinessListScreenRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) {
    fun verifyBusinessIsDisplayed(businessName: String) {
        composeTestRule.onNodeWithText(businessName).assertIsDisplayed()
    }

    fun clickBusinessWithName(businessName: String) {
        composeTestRule.onNodeWithText(businessName).performClick()
    }

    fun verifyLoadingIsShown() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.loading)).assertIsDisplayed()
    }

    fun verifyErrorIsShown() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.error_something_went_wrong)).assertIsDisplayed()
    }
}
