package com.yelpexplorer.core.injection

import android.app.Application
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yelpexplorer.core.utils.Const
import com.yelpexplorer.features.business.data.rest.datasource.remote.BusinessApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { provideOkHttpCache(application = get()) }
    single { provideOkHttpClient(cache = get()) }
    // REST
    single { provideJson() }
    single { provideRetrofit(okHttpClient = get(), json = get()) }
    single { provideBusinessApi(retrofit = get()) }
    // GRAPHQL
    single { provideApolloClient(okHttpClient = get()) }
}

fun provideOkHttpCache(application: Application): Cache {
    val cacheSize = 10 * 1024 * 1024 // 10 MB
    return Cache(application.cacheDir, cacheSize.toLong())
}

fun provideOkHttpClient(cache: Cache): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val headerInterceptor = Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder().method(original.method, original.body)
        builder.addHeader(
            name = "Authorization",
            value = "Bearer ${Const.API_KEY}"
        )
        chain.proceed(builder.build())
    }
    return OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()
}

@OptIn(ExperimentalSerializationApi::class)
fun provideJson(): Json {
    return Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
}

// REST
fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Const.URL_REST)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .client(okHttpClient)
        .build()
}

fun provideBusinessApi(retrofit: Retrofit): BusinessApi {
    return retrofit.create(BusinessApi::class.java)
}

// GRAPHQL
fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
    return ApolloClient.Builder()
        .serverUrl(Const.URL_GRAPHQL)
        .okHttpClient(okHttpClient)
        .build()
}
