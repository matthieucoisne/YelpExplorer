package com.yelpexplorer.injection

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.google.gson.Gson
import com.yelpexplorer.libraries.core.data.local.Const
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val headerInterceptor = Interceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(original.method, original.body)
            builder.addHeader(
                name = "Authorization",
                value = "Bearer ${Const.AUTH_TOKEN}"
            )
            chain.proceed(builder.build())
        }

        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Const.URL_BASE)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(Const.URL_BASE)
            .okHttpClient(okHttpClient)
            .build()
    }
}
