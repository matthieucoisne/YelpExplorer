package com.yelpexplorer.features.business.domain.repository

import androidx.lifecycle.LiveData
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.libraries.core.utils.Resource

interface BusinessRepository {
    suspend fun getBusinessList(): LiveData<Resource<List<Business>>>
    suspend fun getBusinessDetails(businessId: String): LiveData<Resource<Business>>
}