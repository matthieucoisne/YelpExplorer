package com.yelpexplorer.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okio.buffer
import okio.source
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
class FileUtils {
    companion object {
        private fun getStringFromFile(file: File): String {
            file.source().use { source ->
                source.buffer().use { bufferedSource ->
                    return bufferedSource.readUtf8()
                }
            }
        }

        fun getStringFromPath(filePath: String): String {
            val file = File(FileUtils::class.java.classLoader!!.getResource(filePath).path)
            return getStringFromFile(file)
        }

        inline fun <reified T> getDataFromPath(filePath: String): T {
            val json = Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }
            return json.decodeFromString(getStringFromPath(filePath))
        }
    }
}
