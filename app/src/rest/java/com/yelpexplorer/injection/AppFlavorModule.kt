package com.yelpexplorer.injection

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yelpexplorer.features.business.data.remote.BusinessApi
import com.yelpexplorer.libraries.core.data.local.Const
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appFlavorModule = module {
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
    single { provideBusinessApi(get()) }
}

fun provideBusinessApi(retrofit: Retrofit): BusinessApi {
    return retrofit.create(BusinessApi::class.java)
}

fun provideGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    return gsonBuilder.create()
}

fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(Const.URL_REST)
        .client(okHttpClient)
        .build()
}
