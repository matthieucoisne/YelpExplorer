package com.yelpexplorer.runner

import android.app.Application
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yelpexplorer.features.business.data.rest.repository.BusinessRestRepository
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

class MockTestRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)
        loadKoinModules(
            module {
                single<BusinessRepository> {
                    BusinessRestRepository(
                        createTestWebService(okHttpClient = get(), json = get())
                    )
                }
            }
        )
    }

    private inline fun <reified T> createTestWebService(okHttpClient: OkHttpClient, json: Json): T {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:$MOCK_WEB_SERVER_PORT/")
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
        return retrofit.create(T::class.java)
    }

    companion object {
        const val MOCK_WEB_SERVER_PORT = 8337
    }
}
