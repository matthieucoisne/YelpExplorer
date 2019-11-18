package com.yelpexplorer.features.business.domain.usecase

import androidx.lifecycle.LiveData
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.libraries.core.utils.Resource
import javax.inject.Inject

class GetBusinessDetailsUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
) {

    suspend fun execute(businessId: String): LiveData<Resource<Business>> {
        return businessRepository.getBusinessDetails(businessId = businessId)
    }
}
