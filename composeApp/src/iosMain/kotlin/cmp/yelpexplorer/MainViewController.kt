package cmp.yelpexplorer

import androidx.compose.ui.window.ComposeUIViewController
import cmp.yelpexplorer.core.injection.initKoin
import cmp.yelpexplorer.core.theme.YelpExplorerTheme

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    YelpExplorerTheme(darkTheme = true) {
        App()
    }
}
