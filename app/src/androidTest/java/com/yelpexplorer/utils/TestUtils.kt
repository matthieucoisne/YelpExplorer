package com.yelpexplorer.utils

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavController
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert

class TestUtils {
    companion object {
        fun waitFor(millis: Long) {
            // https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473
            Thread.sleep(millis) // this is just to have a visual confirmation all is loaded correctly
        }

        fun ComposeTestRule.printDebug() {
            this.onRoot().printToLog("YelpExplorer")
        }

        fun NavController.assertCurrentRouteName(expectedRouteName: String) {
            Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
        }

        fun MockWebServer.enqueueMockResponse(filePath: String, responseCode: Int = 200) {
            this.enqueue(
                MockResponse()
                    .setResponseCode(responseCode)
                    .setBody(
                        FileUtils.getStringFromFile(
                            context = InstrumentationRegistry.getInstrumentation().context,
                            filePath = filePath,
                        )
                    )
            )
        }
    }
}
