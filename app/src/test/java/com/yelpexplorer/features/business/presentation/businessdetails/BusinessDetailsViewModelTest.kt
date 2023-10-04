package com.yelpexplorer.features.business.presentation.businessdetails

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.yelpexplorer.R
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.usecase.BusinessDetailsUseCase
import com.yelpexplorer.rule.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BusinessDetailsViewModelTest {
    @JvmField @Rule val mainDispatcherRule = MainDispatcherRule()

    @Before fun setUp() {}
    @After fun tearDown() {}

    // https://developer.android.com/kotlin/flow/test#continuous-collection
    private class FakeBusinessDetailsUseCase : BusinessDetailsUseCase {
        private val flow = MutableSharedFlow<Resource<Business>>()
        suspend fun emit(value: Resource<Business>) = flow.emit(value)
        override suspend fun execute(businessId: String): Flow<Resource<Business>> = flow
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
        val expectedBusinessDetailsUiModel = BusinessDetailsUiModel(
            id = "id",
            name = "name",
            photoUrl = "http://",
            rating = 4.5,
            reviewCount = 1337,
            address = "address",
            price = "$$",
            categories = "category#1",
            openingHours = emptyMap(),
            reviews = emptyList()
        )
        val fakeUseCase = FakeBusinessDetailsUseCase()
        val savedState = SavedStateHandle(mapOf("businessId" to "businessId"))
        val viewModel = BusinessDetailsViewModel(fakeUseCase, savedState)

        // ACT & ASSERT
        fakeUseCase.emit(Resource.Loading())
        assertThat(viewModel.uiState.value).isEqualTo(
            BusinessDetailsViewModel.ViewState.ShowLoading
        )
        fakeUseCase.emit(Resource.Success(fakeBusiness))
        assertThat(viewModel.uiState.value).isEqualTo(
            BusinessDetailsViewModel.ViewState.ShowBusinessDetails(
                expectedBusinessDetailsUiModel
            )
        )
    }

    @Test
    fun `init error`() = runTest {
        // ARRANGE
        val fakeUseCase = FakeBusinessDetailsUseCase()
        val savedState = SavedStateHandle(mapOf("businessId" to "businessId"))
        val viewModel = BusinessDetailsViewModel(fakeUseCase, savedState)

        // ACT & ASSERT
        fakeUseCase.emit(Resource.Loading())
        assertThat(viewModel.uiState.value).isEqualTo(
            BusinessDetailsViewModel.ViewState.ShowLoading
        )
        fakeUseCase.emit(Resource.Error("error"))
        assertThat(viewModel.uiState.value).isEqualTo(
            BusinessDetailsViewModel.ViewState.ShowError(R.string.error_something_went_wrong)
        )
    }
}
