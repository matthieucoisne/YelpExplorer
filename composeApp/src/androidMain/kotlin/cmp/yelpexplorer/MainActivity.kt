package cmp.yelpexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cmp.yelpexplorer.core.theme.YelpExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YelpExplorerTheme(darkTheme = true) {
                App()
            }
        }
    }
}
