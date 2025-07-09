package cmp.yelpexplorer.features.business.presentation.businesslist

import app.cash.turbine.test
import cmp.yelpexplorer.features.business.domain.model.Business
import cmp.yelpexplorer.features.business.domain.usecase.BusinessListUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import yelpexplorer_cmp.composeapp.generated.resources.Res
import yelpexplorer_cmp.composeapp.generated.resources.error_something_went_wrong
import kotlin.test.Test
import kotlin.test.assertEquals

class BusinessListViewModelTest {

    // https://developer.android.com/kotlin/flow/test#continuous-collection
    private inner class FakeBusinessListUseCase(private val result: Result<List<Business>>) : BusinessListUseCase {
        override suspend fun execute(
            term: String,
            location: String,
            sortBy: String,
            limit: Int
        ): Result<List<Business>> {
            delay(1) // Delay to ensure the initial state is captured by the test collector.
            return result
        }
    }

    @Test
    fun `init success`() = runTest {
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
        val expectedBusinessUiModel = BusinessUiModel(
            id = "id",
            name = "name",
            address = "address",
            photoUrl = "http://",
            price = "$$",
            categories = "category#1",
            reviewCount = 1337,
            rating = 4.5,
        )
        val viewModel = BusinessListViewModel(
            getBusinessListUseCase = FakeBusinessListUseCase(
                result = Result.success(value = listOf(fakeBusiness))
            )
        )

        // ACT & ASSERT
        viewModel.uiState.test {
            assertEquals(
                expected = BusinessListViewModel.ViewState.ShowLoading(),
                actual = awaitItem()
            )
            assertEquals(
                expected = BusinessListViewModel.ViewState.ShowBusinessList(
                    businessList = BusinessListUiModel(businessList = listOf(expectedBusinessUiModel))
                ),
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `init error`() = runTest {
        // ARRANGE
        val viewModel = BusinessListViewModel(
            getBusinessListUseCase = FakeBusinessListUseCase(
                result = Result.failure(Exception())
            )
        )

        // ACT & ASSERT
        viewModel.uiState.test {
            assertEquals(
                expected = BusinessListViewModel.ViewState.ShowLoading(),
                actual = awaitItem()
            )
            assertEquals(
                expected = BusinessListViewModel.ViewState.ShowError(
                    error = Res.string.error_something_went_wrong
                ),
                actual = awaitItem()
            )
        }
    }
}
