package com.yelpexplorer.libraries.core.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yelpexplorer.libraries.core.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

abstract class NetworkBoundResource<RequestType, ResultType> {

    private val result = MutableLiveData<Resource<ResultType>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkBoundResource<RequestType, ResultType> {
        setValue(Resource.Loading())

        CoroutineScope(coroutineContext).launch(Dispatchers.IO + supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork(dbResult)
                } catch (e: Exception) {
                    setValue(Resource.Error(e.toString(), loadFromDb()))
                }
            } else {
                setValue(Resource.Success(dbResult!!))
            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    private suspend fun fetchFromNetwork(dbResult: ResultType?) {
        setValue(Resource.Loading(dbResult))
        val apiResponse = createCall()
        saveCallResults(processResponse(apiResponse))
        setValue(Resource.Success(loadFromDb()!!))
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun createCall(): RequestType

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): RequestType

    @WorkerThread
    protected abstract suspend fun saveCallResults(data: RequestType)

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType?
}
