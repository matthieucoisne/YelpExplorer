package cmp.yelpexplorer.core.injection

import com.apollographql.apollo.ApolloClient
import com.apollographql.ktor.ktorClient
import cmp.yelpexplorer.core.utils.Const
import cmp.yelpexplorer.core.utils.DataSource
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatcherModule = module {
    single<CoroutineDispatcher>(named(Const.DISPATCHER_DEFAULT)) { Dispatchers.Default }
    single<CoroutineDispatcher>(named(Const.DISPATCHER_IO)) { Dispatchers.IO }
    single<CoroutineDispatcher>(named(Const.DISPATCHER_MAIN)) { Dispatchers.Main }
}

val dataSourceModule = module {
    when (Const.DATASOURCE) {
        DataSource.REST -> {
            single { provideHttpClient(Const.URL_REST) }
        }
        DataSource.GRAPHQL -> {
            single { provideHttpClient(Const.URL_GRAPHQL) }
            singleOf(::provideApolloClient)
        }
    }
}

// This needs to be below the definition of the 2 modules above, otherwise Koin will throw a NPE
val appModule = module {
    includes(
        dispatcherModule,
        dataSourceModule,
    )
}

expect fun provideHttpClient(serverUrl: String): HttpClient

fun provideApolloClient(httpClient: HttpClient): ApolloClient {
    return ApolloClient.Builder()
        .serverUrl(Const.URL_GRAPHQL) // Required even if HttpClient has been set up with the url already.
        .ktorClient(httpClient)
        .build()
}
