package com.yelpexplorer.features.business.presentation.businesslist

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yelpexplorer.R
import com.yelpexplorer.core.utils.Resource
import com.yelpexplorer.features.business.domain.usecase.BusinessListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BusinessListViewModel(
    private val getBusinessListUseCase: BusinessListUseCase
) : ViewModel() {

    sealed class ViewState {
        object ShowLoading : ViewState()
        data class ShowBusinessList(val businessList: BusinessListUiModel) : ViewState()
        data class ShowError(@StringRes val errorStringId: Int) : ViewState()
    }

    private val _uiState = MutableStateFlow<ViewState>(ViewState.ShowLoading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getBusinessListUseCase.execute(
                term = "sushi",
                location = "montreal",
                sortBy = "rating",
                limit = 20
            ).collect { resource ->
                _uiState.value = when (resource) {
                    is Resource.Loading -> ViewState.ShowLoading
                    is Resource.Error -> ViewState.ShowError(R.string.error_something_went_wrong)
                    is Resource.Success -> ViewState.ShowBusinessList(resource.data.toBusinessListUiModel())
                }
            }
        }
    }
}
