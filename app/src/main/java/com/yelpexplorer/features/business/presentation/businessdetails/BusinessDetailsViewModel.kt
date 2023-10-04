package com.yelpexplorer.features.business.presentation.businessdetails

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yelpexplorer.R
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.domain.usecase.BusinessDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BusinessDetailsViewModel(
    getBusinessDetailsUseCase: BusinessDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    sealed class ViewState {
        object ShowLoading : ViewState()
        data class ShowBusinessDetails(val businessDetails: BusinessDetailsUiModel) : ViewState()
        data class ShowError(@StringRes val errorStringId: Int) : ViewState()
    }

    private val _uiState = MutableStateFlow<ViewState>(ViewState.ShowLoading)
    val uiState = _uiState.asStateFlow()

    init {
        // https://developer.android.com/jetpack/compose/navigation#retrieving-complex-data
        val businessId: String = checkNotNull(savedStateHandle["businessId"])

        viewModelScope.launch {
            getBusinessDetailsUseCase.execute(
                businessId = businessId
            ).collect { resource ->
                _uiState.value = when (resource) {
                    is Resource.Loading -> ViewState.ShowLoading
                    is Resource.Error -> ViewState.ShowError(R.string.error_something_went_wrong)
                    is Resource.Success -> ViewState.ShowBusinessDetails(resource.data.toBusinessDetailsUiModel())
                }
            }
        }
    }
}
