package cmp.yelpexplorer.features.business.presentation.businessdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmp.yelpexplorer.features.business.domain.usecase.BusinessDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import yelpexplorer_cmp.composeapp.generated.resources.Res
import yelpexplorer_cmp.composeapp.generated.resources.error_something_went_wrong

class BusinessDetailsViewModel(
    getBusinessDetailsUseCase: BusinessDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    sealed class ViewState {
        data object ShowLoading : ViewState()
        data class ShowBusinessDetails(val businessDetails: BusinessDetailsUiModel) : ViewState()
        data class ShowError(val error: StringResource) : ViewState()
    }

    private val _uiState = MutableStateFlow<ViewState>(ViewState.ShowLoading)
    val uiState = _uiState.asStateFlow()

    init {
        // https://developer.android.com/jetpack/compose/navigation#retrieving-complex-data
        val businessId: String = checkNotNull(savedStateHandle["businessId"])

        _uiState.value = ViewState.ShowLoading
        viewModelScope.launch {
            val result = getBusinessDetailsUseCase.execute(
                businessId = businessId
            )
            _uiState.value = result.fold(
                onSuccess = {
                    ViewState.ShowBusinessDetails(it.toBusinessDetailsUiModel())
                },
                onFailure = {
                    ViewState.ShowError(Res.string.error_something_went_wrong)
                },
            )
        }
    }
}
