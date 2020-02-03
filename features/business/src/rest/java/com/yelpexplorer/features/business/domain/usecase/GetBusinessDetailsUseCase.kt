package com.yelpexplorer.features.business.domain.usecase

import com.yelpexplorer.features.business.domain.model.Business
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import com.yelpexplorer.libraries.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetBusinessDetailsUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
) {

    suspend fun execute(businessId: String): Flow<Resource<Business>> {
        val businessDetails = businessRepository.getBusinessDetails(businessId = businessId)
        val businessReviews = businessRepository.getBusinessReviews(businessId = businessId)

        return businessDetails.combineTransform(businessReviews) { details, reviews ->
            if (details is Resource.Loading || reviews is Resource.Loading) {
                emit(Resource.Loading<Business>())
            } else if (details is Resource.Success && reviews is Resource.Success) {
                val business = details.data.copy(reviews = reviews.data)
                emit(Resource.Success<Business>(business))
            } else {
                val errorMessage = StringBuilder()
                if (details is Resource.Error) {
                    errorMessage.append(details.errorMessage)
                }
                if (reviews is Resource.Error) {
                    if (errorMessage.isNotBlank()) {
                        errorMessage.append("\n")
                    }
                    errorMessage.append(reviews.errorMessage)
                }
                emit(Resource.Error<Business>(errorMessage.toString()))
            }
        }.distinctUntilChanged()
    }
}
