package com.yelpexplorer.injection

import com.apollographql.apollo.ApolloClient
import com.yelpexplorer.libraries.core.data.local.Const
import okhttp3.OkHttpClient
import toothpick.ProvidesSingletonInScope
import toothpick.config.Module
import toothpick.ktp.binding.bind
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class FlavorModule : Module() {
    init {
        bind<ApolloClient>().toProvider(ApolloClientProvider::class)
    }
}

@Singleton
@ProvidesSingletonInScope
class ApolloClientProvider @Inject constructor(
    private val okHttpClient: OkHttpClient
) : Provider<ApolloClient> {

    override fun get(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(Const.URL_GRAPHQL)
            .okHttpClient(okHttpClient)
            .build()
    }
}
