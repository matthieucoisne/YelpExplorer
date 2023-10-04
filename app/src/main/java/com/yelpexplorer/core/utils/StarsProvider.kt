package com.yelpexplorer.core.utils

import com.yelpexplorer.R

class StarsProvider {
    companion object {
        fun getDrawableId(rating: Double) = when (rating) {
            1.0 -> R.drawable.stars_small_1
            1.5 -> R.drawable.stars_small_1_half
            2.0 -> R.drawable.stars_small_2
            2.5 -> R.drawable.stars_small_2_half
            3.0 -> R.drawable.stars_small_3
            3.5 -> R.drawable.stars_small_3_half
            4.0 -> R.drawable.stars_small_4
            4.5 -> R.drawable.stars_small_4_half
            5.0 -> R.drawable.stars_small_5
            else -> R.drawable.stars_small_0
        }
    }
}
