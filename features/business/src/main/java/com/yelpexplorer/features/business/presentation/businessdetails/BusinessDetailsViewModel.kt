package com.yelpexplorer.features.business.presentation.businessdetails

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.yelpexplorer.features.business.R
import com.yelpexplorer.features.business.domain.usecase.GetBusinessDetailsUseCase
import com.yelpexplorer.libraries.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BusinessDetailsViewModel @Inject constructor(
    private val getBusinessDetailsUseCase: GetBusinessDetailsUseCase
) : ViewModel() {

    sealed class ViewState {
        data class ShowLoading(val businessDetails: BusinessDetailsUiModel?) : ViewState()
        data class ShowBusinessDetails(val businessDetails: BusinessDetailsUiModel) : ViewState()
        data class ShowError(@StringRes val errorStringId: Int) : ViewState()
    }

    private val _viewState: MutableLiveData<ViewState>
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val businessId = MutableLiveData<String>()

    init {
        _viewState = businessId.switchMap { businessId ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.Main) {
                emitSource(getBusinessDetailsUseCase.execute(businessId))
            }
        }.map { resource ->
            when (resource) {
                is Resource.Loading -> ViewState.ShowLoading(resource.data?.toBusinessDetailsUiModel())
                is Resource.Error -> ViewState.ShowError(R.string.error_something_went_wrong)
                is Resource.Success -> ViewState.ShowBusinessDetails(resource.data.toBusinessDetailsUiModel())
            }
        } as MutableLiveData<ViewState>
    }

    fun setBusinessId(businessId: String?) {
        if (this.businessId.value != businessId) {
            this.businessId.value = businessId
        }
    }
}
