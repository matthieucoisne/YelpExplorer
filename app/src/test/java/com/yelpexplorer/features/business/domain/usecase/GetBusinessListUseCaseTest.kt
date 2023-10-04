package com.yelpexplorer.features.business.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetBusinessListUseCaseTest {
    @Before fun setUp() {}
    @After fun tearDown() {}

    @Test
    fun `execute with success`() = runTest {
        // ARRANGE
        val fakeBusiness = Business(
            id = "id",
            name = "name",
            address = "address",
            photoUrl = "http://",
            price = "$$",
            categories = listOf("category#1"),
            reviewCount = 1337,
            rating = 4.5,
            hours = null,
            reviews = null,
        )
        val businessList = listOf(fakeBusiness, fakeBusiness)
        val fakeBusinessRepository = object: BusinessRepository { // Could also use mock() and `when().thenReturn()`
            override suspend fun getBusinessList(term: String, location: String, sortBy: String, limit: Int): Flow<Resource<List<Business>>> {
                return flowOf(
                    Resource.Loading(),
                    Resource.Success(businessList)
                )
            }
            override suspend fun getBusinessDetailsWithReviews(businessId: String) = throw Error("IGNORED")
        }
        val useCase = GetBusinessListUseCase(fakeBusinessRepository)

        // ACT
        val result = useCase.execute(
            term = "term",
            location = "location",
            sortBy = "sortBy",
            limit = businessList.size
        )

        // ASSERT
        result.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading<List<Business>>())
            assertThat(awaitItem()).isEqualTo(Resource.Success(businessList))
            awaitComplete()
        }
    }

    @Test
    fun `execute with error`() = runTest {
        // ARRANGE
        val fakeBusinessRepository = object: BusinessRepository { // Could also use mock() and `when().thenReturn()`
            override suspend fun getBusinessList(term: String, location: String, sortBy: String, limit: Int): Flow<Resource<List<Business>>> {
                return flowOf(
                    Resource.Loading(),
                    Resource.Error(errorMessage = "errorMessage")
                )
            }
            override suspend fun getBusinessDetailsWithReviews(businessId: String) = throw Error("IGNORED")
        }
        val useCase = GetBusinessListUseCase(fakeBusinessRepository)

        // ACT
        val result = useCase.execute(
            term = "term",
            location = "location",
            sortBy = "sortBy",
            limit = 2
        )

        // ASSERT
        result.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading<List<Business>>())
            assertThat(awaitItem()).isEqualTo(Resource.Error<List<Business>>(errorMessage= "errorMessage"))
            awaitComplete()
        }
    }
}
