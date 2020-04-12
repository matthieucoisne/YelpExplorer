package com.yelpexplorer.injection

import android.app.Application
import com.yelpexplorer.libraries.core.data.local.Const
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val appModule = module {
    single { provideOkHttpCache(get()) }
    single { provideOkHttpClient(get()) }
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
