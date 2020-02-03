package com.yelpexplorer.injection

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yelpexplorer.features.business.data.remote.BusinessApi
import com.yelpexplorer.libraries.core.data.local.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.ProvidesSingletonInScope
import toothpick.config.Module
import toothpick.ktp.binding.bind
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class FlavorModule : Module() {
    init {
        bind<Gson>().toProvider(GsonProvider::class)
        bind<Retrofit>().toProvider(RetrofitProvider::class)
        bind<BusinessApi>().toProvider(BusinessApiProvider::class)
    }
}

@Singleton
@ProvidesSingletonInScope
class GsonProvider : Provider<Gson> {

    override fun get(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }
}

@Singleton
@ProvidesSingletonInScope
class RetrofitProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson
) : Provider<Retrofit> {

    override fun get(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Const.URL_REST)
            .client(okHttpClient)
            .build()
    }
}

@Singleton
@ProvidesSingletonInScope
class BusinessApiProvider @Inject constructor(
    private val retrofit: Retrofit
) : Provider<BusinessApi> {

    override fun get(): BusinessApi {
        return retrofit.create(BusinessApi::class.java)
    }
}
