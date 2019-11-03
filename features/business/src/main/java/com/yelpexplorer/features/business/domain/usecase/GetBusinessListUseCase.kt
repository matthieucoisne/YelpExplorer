package com.yelpexplorer.features.business.domain.usecase

import androidx.lifecycle.LiveData
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.libraries.core.utils.Resource

class GetBusinessListUseCase(
    private val businessRepository: BusinessRepository
) {

    suspend fun execute(): LiveData<Resource<List<Business>>> {
        return businessRepository.getBusinessList()
    }
}
