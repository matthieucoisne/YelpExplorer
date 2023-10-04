package com.yelpexplorer.features.business

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule

abstract class BusinessScreenTest {
    @get:Rule val composeTestRule = createAndroidComposeRule<ComponentActivity>()
}
