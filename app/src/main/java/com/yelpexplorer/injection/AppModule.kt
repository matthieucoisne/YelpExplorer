package com.yelpexplorer.injection

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.yelpexplorer.features.business.data.repository.BusinessDataRepository
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.libraries.core.data.local.Const
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import toothpick.ProvidesSingletonInScope
import toothpick.config.Module
import toothpick.ktp.binding.bind
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class AppModule : Module() {
    init {
        bind<Cache>().toProvider(OkHttpCacheProvider::class)
        bind<OkHttpClient>().toProvider(OkHttpClientProvider::class)
        bind<ApolloClient>().toProvider(ApolloClientProvider::class)

        bind<BusinessRepository>().toClass(BusinessDataRepository::class)
    }
}

@Singleton
@ProvidesSingletonInScope
class OkHttpCacheProvider @Inject constructor(
    private val application: Application
) : Provider<Cache> {

    override fun get(): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(application.cacheDir, cacheSize.toLong())
    }
}

@Singleton
@ProvidesSingletonInScope
class OkHttpClientProvider @Inject constructor(
    private val cache: Cache
) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
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
}

@Singleton
@ProvidesSingletonInScope
class ApolloClientProvider @Inject constructor(
    private val okHttpClient: OkHttpClient
) : Provider<ApolloClient> {

    override fun get(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(Const.URL_BASE)
            .okHttpClient(okHttpClient)
            .build()
    }
}
