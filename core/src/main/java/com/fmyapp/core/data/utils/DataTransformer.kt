package com.fmyapp.core.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class DataTransformer<ResponseType, OutputType> {
    private var result: Flow<ResultState<OutputType>> = flow {
        emit(ResultState.Loading())

        when (val response = fetchData().first()) {
            is RemoteResult.Success -> {
                emit(ResultState.Success(transformData(response.data)))
            }
            is RemoteResult.Empty -> {
                emit(ResultState.Success(transformData(null)))
            }
            is RemoteResult.Error -> {
                emit(ResultState.Error(response.errorMessage))
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract suspend fun fetchData(): Flow<RemoteResult<ResponseType>>

    protected abstract fun transformData(data: ResponseType?): OutputType

    fun asFlow(): Flow<ResultState<OutputType>> = result
}