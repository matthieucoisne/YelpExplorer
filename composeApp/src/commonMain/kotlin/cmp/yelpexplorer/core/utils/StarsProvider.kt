package cmp.yelpexplorer.core.utils

import yelpexplorer_cmp.composeapp.generated.resources.Res
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_0
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_1
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_1_half
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_2
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_2_half
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_3
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_3_half
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_4
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_4_half
import yelpexplorer_cmp.composeapp.generated.resources.stars_small_5

class StarsProvider {
    companion object {
        fun getDrawableResource(rating: Double) = when (rating) {
            in 0.8 .. 1.2 -> Res.drawable.stars_small_1
            in 1.3..1.7 -> Res.drawable.stars_small_1_half
            in 1.8..2.2 -> Res.drawable.stars_small_2
            in 2.3..2.7 -> Res.drawable.stars_small_2_half
            in 2.8..3.2 -> Res.drawable.stars_small_3
            in 3.3..3.7 -> Res.drawable.stars_small_3_half
            in 3.8..4.2 -> Res.drawable.stars_small_4
            in 4.3..4.7 -> Res.drawable.stars_small_4_half
            in 4.8..5.0 -> Res.drawable.stars_small_5
            else -> Res.drawable.stars_small_0
        }
    }
}
