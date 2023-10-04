package com.yelpexplorer.features.business.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.model.Review
import com.yelpexplorer.features.business.domain.model.User
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetBusinessDetailsUseCaseTest {
    @Before fun setUp() {}
    @After fun tearDown() {}

    private val fakeReview = Review(
        user = User(
            name = "name",
            imageUrl = "photoUrl",
        ),
        text = "text",
        rating = 5,
        timeCreated = "01-01-2020",
    )
    private val fakeReviews = listOf(fakeReview, fakeReview)
    private val fakeBusiness = Business(
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
    private val expectedBusiness = fakeBusiness.copy(reviews = fakeReviews)

    @Test
    fun `execute with success using a fake repository`() = runTest {
        // ARRANGE
        val fakeBusinessRepository = object: BusinessRepository {
            override suspend fun getBusinessList(term: String, location: String, sortBy: String, limit: Int) = throw Error("IGNORED")
            override suspend fun getBusinessDetailsWithReviews(businessId: String): Flow<Resource<Business>> {
                return flow {
                    emit(Resource.Loading())
                    emit(Resource.Success(fakeBusiness.copy(reviews = fakeReviews)))
                }
            }
        }
        val useCase = GetBusinessDetailsUseCase(fakeBusinessRepository)

        // ACT
        val result = useCase.execute(businessId = "businessId")

        // ASSERT
        result.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading<Business>())
            assertThat(awaitItem()).isEqualTo(Resource.Success(expectedBusiness))
            awaitComplete()
        }
    }

    @Test
    fun `execute with success with a mock repository`() = runTest {
        // ARRANGE
        val mockBusinessRepository: BusinessRepository = mock()
        whenever(mockBusinessRepository.getBusinessDetailsWithReviews(anyString())).thenReturn(
            flowOf(
                Resource.Loading(),
                Resource.Success(fakeBusiness.copy(reviews = fakeReviews))
            )
        )
        val useCase = GetBusinessDetailsUseCase(mockBusinessRepository)

        // ACT
        val result = useCase.execute(businessId = "businessId")

        // ASSERT
        result.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading<Business>())
            assertThat(awaitItem()).isEqualTo(Resource.Success(expectedBusiness))
            awaitComplete()
        }
    }
}
