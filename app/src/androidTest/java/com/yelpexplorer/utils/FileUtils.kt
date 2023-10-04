package com.yelpexplorer.utils

import android.content.Context
import okio.buffer
import okio.source

class FileUtils {
    companion object {
        fun getStringFromFile(context: Context, filePath: String): String {
            context.assets.open(filePath).source().use { source ->
                source.buffer().use { bufferedSource ->
                    return bufferedSource.readUtf8()
                }
            }
        }
    }
}
