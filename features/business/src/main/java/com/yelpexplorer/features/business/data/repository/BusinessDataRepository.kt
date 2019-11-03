package com.yelpexplorer.features.business.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.libraries.core.utils.Resource
import javax.inject.Inject

class BusinessDataRepository @Inject constructor(
) : BusinessRepository {

    override suspend fun getBusinessList(): LiveData<Resource<List<Business>>> {
        // TODO
        return MutableLiveData<Resource<List<Business>>>()
    }

    override suspend fun getBusinessDetails(businessId: String): LiveData<Resource<Business>> {
        // TODO
        return MutableLiveData<Resource<Business>>()
    }
}
