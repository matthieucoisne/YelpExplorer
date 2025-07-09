package cmp.yelpexplorer.features.business.presentation.businesslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmp.yelpexplorer.features.business.domain.usecase.BusinessListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import yelpexplorer_cmp.composeapp.generated.resources.Res
import yelpexplorer_cmp.composeapp.generated.resources.error_something_went_wrong

class BusinessListViewModel(
    private val getBusinessListUseCase: BusinessListUseCase
) : ViewModel() {

    sealed class ViewState {
        data class ShowLoading(val businessList: BusinessListUiModel? = null) : ViewState()
        data class ShowBusinessList(val businessList: BusinessListUiModel) : ViewState()
        data class ShowError(val error: StringResource) : ViewState()
    }

    private val _uiState = MutableStateFlow<ViewState>(ViewState.ShowLoading())
    val uiState = _uiState.asStateFlow()

    init {
        getBusinessList()
    }

    fun getBusinessList() {
        _uiState.value = ViewState.ShowLoading(
            businessList = (_uiState.value as? ViewState.ShowBusinessList)?.businessList
        )
        viewModelScope.launch {
            val result = getBusinessListUseCase.execute(
                term = "sushi",
                location = "montreal",
                sortBy = "rating",
                limit = 20
            )
            _uiState.value = result.fold(
                onSuccess = {
                    ViewState.ShowBusinessList(businessList = it.toBusinessListUiModel())
                },
                onFailure = {
                    ViewState.ShowError(error = Res.string.error_something_went_wrong)
                }
            )
        }
    }
}
