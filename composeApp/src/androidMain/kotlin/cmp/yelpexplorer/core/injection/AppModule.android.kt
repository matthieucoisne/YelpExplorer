package cmp.yelpexplorer.core.injection

import cmp.yelpexplorer.core.utils.Const
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual fun provideHttpClient(serverUrl: String): HttpClient = HttpClient(OkHttp) {
    install(HttpTimeout) {
        socketTimeoutMillis = 15_000
        requestTimeoutMillis = 15_000
    }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println("Android HttpClient: $message")
            }
        }
    }

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        )
    }

    defaultRequest {
        header("Authorization", "Bearer ${Const.API_KEY}")
        header("Content-Type", "application/json")
        header("Accept-Language", "en-US")

        url(serverUrl)
    }
}