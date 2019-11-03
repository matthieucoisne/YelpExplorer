package com.yelpexplorer.libraries.core.utils

import android.content.Context
import android.content.Intent

object Router {

    fun getSettingsIntent(context: Context) = internalIntent(context, "com.yelpexplorer.features.settings.open")
//        .putExtra()

    private fun internalIntent(context: Context, action: String) = Intent(action).setPackage(context.packageName)
}
