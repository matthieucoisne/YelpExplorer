package com.yelpexplorer.features.business.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.mockserver.MockResponse
import com.apollographql.apollo3.mockserver.MockServer
import com.google.common.truth.Truth.assertThat
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.data.graphql.repository.BusinessGraphQLRepository
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.utils.FileUtils
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class BusinessGraphQLRepositoryTest : BusinessRepositoryTest() {

    @Before fun setUp() {}
    @After fun tearDown() {}

    @Test
    fun `get business list success`() = runTest {
        // ARRANGE
        val expectedBusinessList = listOf(expectedBusiness)

        val mockServer = MockServer()
        val apolloClient = ApolloClient.Builder()
            .serverUrl(mockServer.url())
            .build()
        val jsonBusiness = FileUtils.getStringFromPath("responses/graphql/businessList.json")
        mockServer.enqueue(
            MockResponse.Builder()
                .body(jsonBusiness)
                .build()
        )
        val repository = BusinessGraphQLRepository(apolloClient)

        // ACT
        val businessList = repository.getBusinessList("term", "location", "sortBy", 2)

        // ASSERT
        val result = mutableListOf<Resource<List<Business>>>()
        businessList.toList(result)
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo(Resource.Loading<List<Business>>())
        assertThat(result[1]).isEqualTo(Resource.Success<List<Business>>(expectedBusinessList))

        // TODO: Seems like something is wrong when using apollo and turbine: this will timeout
//        businessList.test {
//            assertThat(awaitItem()).isEqualTo(Resource.Loading<List<Business>>())
//            assertThat(awaitItem()).isEqualTo(Resource.Success<List<Business>>(expectedBusinessList))
//            awaitComplete()
//        }

        mockServer.stop()
    }

    @Test
    fun `get business list error 500`() = runTest {
        // ARRANGE
        val mockServer = MockServer()
        val apolloClient = ApolloClient.Builder()
            .serverUrl(mockServer.url())
            .build()
        mockServer.enqueue(
            MockResponse.Builder()
                .statusCode(500)
                .body("Internal server error")
                .build()
        )
        val repository = BusinessGraphQLRepository(apolloClient)

        // ACT
        val businessList = repository.getBusinessList("term", "location", "sortBy", 2)

        // ASSERT
        val result = mutableListOf<Resource<List<Business>>>()
        businessList.toList(result)
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo(Resource.Loading<List<Business>>())
        assertThat(result[1]).isEqualTo(Resource.Error<List<Business>>(errorMessage = "ERROR: Http request failed with status code `500`"))

        // TODO: use Turbine?

        mockServer.stop()
    }

    @Test
    fun `get business details with reviews success`() = runTest {
        // ARRANGE
        val mockServer = MockServer()
        val apolloClient = ApolloClient.Builder()
            .serverUrl(mockServer.url())
            .build()
        val jsonBusinessDetailsWithReviews = FileUtils.getStringFromPath("responses/graphql/businessDetailsWithReviews.json")
        mockServer.enqueue(
            MockResponse.Builder()
                .body(jsonBusinessDetailsWithReviews)
                .build()
        )
        val repository = BusinessGraphQLRepository(apolloClient)

        // ACT
        val businessDetailsWithReviews = repository.getBusinessDetailsWithReviews("businessId")

        // ASSERT
        val result = mutableListOf<Resource<Business>>()
        businessDetailsWithReviews.toList(result)
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo(Resource.Loading<List<Business>>())
        assertThat(result[1]).isEqualTo(Resource.Success<Business>(expectedBusinessDetailsWithReviews))

        // TODO: use Turbine?

        mockServer.stop()
    }

    @Test
    fun `get business details error 500`() = runTest {
        // ARRANGE
        val mockServer = MockServer()
        val apolloClient = ApolloClient.Builder()
            .serverUrl(mockServer.url())
            .build()
        mockServer.enqueue(
            MockResponse.Builder()
                .statusCode(500)
                .body("Internal server error")
                .build()
        )
        val repository = BusinessGraphQLRepository(apolloClient)

        // ACT
        val businessDetailsWithReviews = repository.getBusinessDetailsWithReviews("businessId")

        // ASSERT
        val result = mutableListOf<Resource<Business>>()
        businessDetailsWithReviews.toList(result)
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo(Resource.Loading<List<Business>>())
        assertThat(result[1]).isEqualTo(Resource.Error<List<Business>>(errorMessage = "ERROR: Http request failed with status code `500`"))

        // TODO: use Turbine?

        mockServer.stop()
    }
}
