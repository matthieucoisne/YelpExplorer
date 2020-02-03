package com.yelpexplorer.features.business.domain.injection

import com.yelpexplorer.features.business.data.repository.GraphQLBusinessRepository
import com.yelpexplorer.features.business.domain.repository.BusinessRepository
import toothpick.config.Module
import toothpick.ktp.binding.bind

class BusinessModule : Module() {
    init {
        bind<BusinessRepository>().toClass(GraphQLBusinessRepository::class)
    }
}
