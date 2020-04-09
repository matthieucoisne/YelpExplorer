package com.yelpexplorer.injection

import com.apollographql.apollo.ApolloClient
import com.yelpexplorer.libraries.core.data.local.Const
import okhttp3.OkHttpClient
import org.koin.dsl.module

val appFlavorModule = module {
    single { provideApolloClient(get()) }
}

fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
    return ApolloClient.builder()
        .serverUrl(Const.URL_GRAPHQL)
        .okHttpClient(okHttpClient)
        .build()
}
