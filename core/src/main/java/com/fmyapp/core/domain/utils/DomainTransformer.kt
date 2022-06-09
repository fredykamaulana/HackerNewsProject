package com.fmyapp.core.domain.utils

import com.fmyapp.core.data.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class DomainTransformer<ResultDomain, ResultUi> {

    private val result: Flow<ResultState<ResultUi>> = flow {
        emit(ResultState.Loading())

        when (val response = fetchData().first()) {
            is ResultState.Success -> {
                emit(ResultState.Success(transformData(response.data)))
            }
            is ResultState.Error -> {
                emit(ResultState.Success(transformData(null)))
            }
            else -> {}
        }
    }

    protected abstract suspend fun fetchData(): Flow<ResultState<ResultDomain>>

    protected abstract fun transformData(data: ResultDomain?): ResultUi

    fun asFlow(): Flow<ResultState<ResultUi>> = result
}