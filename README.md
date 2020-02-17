# YelpExplorer

[![build](https://github.com/matthieucoisne/YelpExplorer/workflows/build/badge.svg)](https://github.com/matthieucoisne/YelpExplorer/blob/master/.github/workflows/build.yml)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.3.61-blue.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

## Project Description

YelpExplorer is an Android application that shows a list of business, their details and latest reviews using [Yelp](https://www.yelp.com/)'s API.

The goal of this project is to demonstrate the differences between using a REST API versus a GraphQL API in a modern Android application, that has a modular, scalable, maintainable and testable architecture.

## Project Characteristics

* 100% [Kotlin](https://kotlinlang.org/)
* Modern architecture (feature modules, Clean Architecture, Model-View-ViewModel)
* [Android Jetpack](https://developer.android.com/jetpack)
* Testing
* Material design
* Continuous Integration with GitHub [Actions](https://github.com/matthieucoisne/YelpExplorer/actions)
* Project management with GitHub [Project Board](https://github.com/matthieucoisne/YelpExplorer/projects/1)

## Tech Stack

* Tech Stack
    * [Kotlin](https://kotlinlang.org/)
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
    * [Koin](https://github.com/InsertKoinIO/koin)
    * [Retrofit](https://square.github.io/retrofit) - Used in the `rest` flavor
    * [ApolloGraphQL](https://github.com/apollographql/apollo-android) - Used in the `graphql` flavor
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Glide](https://github.com/bumptech/glide)
    * [and more...](https://github.com/matthieucoisne/YelpExplorer/blob/master/buildSrc/src/main/kotlin/dependencies.kt)
* Architecture
    * Clean Architecture (at module level)
    * MVVM
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture)

## Development Setup

### Yelp API Key

If you want to run this project on an Android device or emulator, you need to obtain your own API key from Yelp and provide it to the app.

1. Request your key: https://www.yelp.com/developers/documentation/v3/authentication
2. Update [Const.kt](https://github.com/matthieucoisne/YelpExplorer/blob/master/libraries/core/src/main/java/com/yelpexplorer/libraries/core/data/local/Const.kt) with your key

### Flavors: Switching APIs

This project allows you to use either the GraphQL API or the REST API by switching between [product flavors](https://developer.android.com/studio/build/build-variants#product-flavors).

Because this project implements Clean Architecture:

* The `data` layer is the one that has the most changes between the 2 flavors
* The `domain` layer needs to have different implementations of `GetBusinessDetailsUseCase`:
  * Using [REST](https://github.com/matthieucoisne/YelpExplorer/blob/master/features/business/src/rest/java/com/yelpexplorer/features/business/domain/usecase/GetBusinessDetailsUseCase.kt), we have to merge 2 api call results: `getBusinessDetails()` and `getBusinessReviews()`
  * Using [GraphQL](https://github.com/matthieucoisne/YelpExplorer/blob/master/features/business/src/graphql/java/com/yelpexplorer/features/business/domain/usecase/GetBusinessDetailsUseCase.kt), we have all the data we need with a single `BusinessDetails` query
* The `presentation` layer remains the same

Specific GraphQL files used by the `graphql` flavor:\
https://github.com/matthieucoisne/YelpExplorer/tree/master/features/business/src/graphql
\
Specific REST files used by the `rest` flavor:\
https://github.com/matthieucoisne/YelpExplorer/tree/master/features/business/src/rest
\
Common files used by both flavors:\
https://github.com/matthieucoisne/YelpExplorer/tree/master/features/business/src/main

## Author

[![Follow me](https://img.shields.io/twitter/follow/matthieucoisne?style=social)](https://twitter.com/matthieucoisne)

## License
```
Copyright 2019 Matthieu Coisne

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
