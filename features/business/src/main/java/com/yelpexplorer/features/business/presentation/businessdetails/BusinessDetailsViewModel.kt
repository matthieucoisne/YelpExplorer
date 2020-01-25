package com.yelpexplorer.features.business.presentation.businessdetails

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.yelpexplorer.features.business.R
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.usecase.GetBusinessDetailsUseCase
import com.yelpexplorer.libraries.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BusinessDetailsViewModel : ViewModel() {

    @Inject lateinit var getBusinessDetailsUseCase: GetBusinessDetailsUseCase

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
            liveData<Resource<Business>>(context = viewModelScope.coroutineContext + Dispatchers.Main) {
                emitSource(
                    getBusinessDetailsUseCase.execute(
                        businessId = businessId
                    ).asLiveData()
                )
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
