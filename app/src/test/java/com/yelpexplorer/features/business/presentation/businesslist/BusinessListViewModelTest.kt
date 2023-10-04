package com.yelpexplorer.features.business.presentation.businesslist

import com.google.common.truth.Truth.assertThat
import com.yelpexplorer.R
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.usecase.BusinessListUseCase
import com.yelpexplorer.rule.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BusinessListViewModelTest {
    @JvmField @Rule val mainDispatcherRule = MainDispatcherRule()

    @Before fun setUp() {}
    @After fun tearDown() {}

    // https://developer.android.com/kotlin/flow/test#continuous-collection
    private class FakeBusinessListUseCase : BusinessListUseCase {
        private val flow = MutableSharedFlow<Resource<List<Business>>>()
        suspend fun emit(value: Resource<List<Business>>) = flow.emit(value)
        override suspend fun execute(
            term: String,
            location: String,
            sortBy: String,
            limit: Int
        ): Flow<Resource<List<Business>>> = flow
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
        val fakeUseCase = FakeBusinessListUseCase()
        val viewModel = BusinessListViewModel(fakeUseCase)

        // ACT & ASSERT
        fakeUseCase.emit(Resource.Loading())
        assertThat(viewModel.uiState.value).isEqualTo(
            BusinessListViewModel.ViewState.ShowLoading
        )
        fakeUseCase.emit(Resource.Success(listOf(fakeBusiness)))
        assertThat(viewModel.uiState.value).isEqualTo(
            BusinessListViewModel.ViewState.ShowBusinessList(
                BusinessListUiModel(listOf(expectedBusinessUiModel))
            )
        )
    }

    @Test
    fun `init error`() = runTest {
        // ARRANGE
        val fakeUseCase = FakeBusinessListUseCase()
        val viewModel = BusinessListViewModel(fakeUseCase)

        // ACT & ASSERT
        fakeUseCase.emit(Resource.Loading())
        assertThat(viewModel.uiState.value).isEqualTo(
            BusinessListViewModel.ViewState.ShowLoading
        )
        fakeUseCase.emit(Resource.Error("error"))
        assertThat(viewModel.uiState.value).isEqualTo(
            BusinessListViewModel.ViewState.ShowError(R.string.error_something_went_wrong)
        )
    }
}
