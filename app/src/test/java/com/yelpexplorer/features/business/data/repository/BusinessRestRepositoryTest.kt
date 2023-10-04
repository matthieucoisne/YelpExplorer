package com.yelpexplorer.features.business.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.data.rest.datasource.remote.BusinessApi
import com.yelpexplorer.features.business.data.rest.model.BusinessEntity
import com.yelpexplorer.features.business.data.rest.model.BusinessListResponse
import com.yelpexplorer.features.business.data.rest.model.ReviewListResponse
import com.yelpexplorer.features.business.data.rest.repository.BusinessRestRepository
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.utils.FileUtils
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.lang.IllegalStateException

class BusinessRestRepositoryTest : BusinessRepositoryTest() {

    private val api = mock<BusinessApi>()

    @Before fun setUp() {}
    @After fun tearDown() {}

    @Test
    fun `get business list success`() = runTest {
        // ARRANGE
        val businessListResponse = FileUtils.getDataFromPath<BusinessListResponse>("responses/rest/getBusinessList.json")
        whenever(api.getBusinessList(anyString(), anyString(), anyString(), anyInt())).thenReturn(
            businessListResponse
        )
        val repository = BusinessRestRepository(api)

        // ACT
        val response = repository.getBusinessList("term", "location", "sortBy", 2)

        // ASSERT
        response.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading<List<Business>>())
            assertThat(awaitItem()).isEqualTo(Resource.Success<List<Business>>(listOf(expectedBusiness)))
            awaitComplete()
        }
    }

    @Test
    fun `get business list error`() = runTest {
        // ARRANGE
        whenever(api.getBusinessList(anyString(), anyString(), anyString(), anyInt())).thenThrow(
            IllegalStateException("error")
        )
        val repository = BusinessRestRepository(api)

        // ACT
        val response = repository.getBusinessList("term", "location", "sortBy", 2)

        // ASSERT
        response.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading<List<Business>>())
            assertThat(awaitItem()).isEqualTo(Resource.Error(errorMessage = "ERROR: error", data = null))
            awaitComplete()
        }
    }

    @Test
    fun `get business details with reviews success`() = runTest {
        // ARRANGE
        val businessDetailsResponse = FileUtils.getDataFromPath<BusinessEntity>("responses/rest/getBusinessDetails.json")
        val reviewListResponse = FileUtils.getDataFromPath<ReviewListResponse>("responses/rest/getBusinessReviews.json")
        whenever(api.getBusinessDetails(anyString())).thenReturn(
            businessDetailsResponse
        )
        whenever(api.getBusinessReviews(anyString())).thenReturn(
            reviewListResponse
        )
        val repository = BusinessRestRepository(api)

        // ACT
        val response = repository.getBusinessDetailsWithReviews("businessId")

        // ASSERT
        response.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading<Business>())
            assertThat(awaitItem()).isEqualTo(Resource.Success(expectedBusinessDetailsWithReviews))
            awaitComplete()
        }
    }

    @Test
    fun `get business details with reviews throws an exception when calling getBusinessDetails`() = runTest {
        // ARRANGE
        val reviewListResponse = FileUtils.getDataFromPath<ReviewListResponse>("responses/rest/getBusinessReviews.json")
        whenever(api.getBusinessDetails(anyString())).thenThrow(IllegalStateException("error"))
        whenever(api.getBusinessReviews(anyString())).thenReturn(
            reviewListResponse
        )
        val repository = BusinessRestRepository(api)

        // ACT
        val response = repository.getBusinessDetailsWithReviews("businessId")

        // ASSERT
        response.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading<Business>())
            assertThat(awaitItem()).isEqualTo(Resource.Error(errorMessage = "ERROR: error", data = null))
            awaitComplete()
        }
    }

    @Test
    fun `get business details with reviews throws an exception when calling getBusinessReviews`() = runTest {
        // ARRANGE
        val businessDetailsResponse = FileUtils.getDataFromPath<BusinessEntity>("responses/rest/getBusinessDetails.json")
        whenever(api.getBusinessDetails(anyString())).thenReturn(
            businessDetailsResponse
        )
        whenever(api.getBusinessReviews(anyString())).thenThrow(IllegalStateException("error"))
        val repository = BusinessRestRepository(api)

        // ACT
        val response = repository.getBusinessDetailsWithReviews("businessId")

        // ARRANGE
        response.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading<Business>())
            assertThat(awaitItem()).isEqualTo(Resource.Error(errorMessage = "ERROR: error", data = null))
            awaitComplete()
        }
    }
}
