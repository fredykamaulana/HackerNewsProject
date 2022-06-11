package com.fmyapp.core.data.utils

import com.fmyapp.core.data.response.BaseResponseDto
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class SafeApiCall {
    open suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Flow<RemoteResult<T>> {
        return flow {
            try {
                val result = apiCall.invoke()
                emit(RemoteResult.Success(result))

            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        when (throwable.code()) {
                            in 400..451 -> parseHttpError(throwable)
                            in 500..599 -> error(
                                HttpState.SERVER_ERROR,
                                throwable.code(),
                                "Server Error"
                            )
                            else -> error(
                                HttpState.NOT_DEFINED,
                                throwable.code(),
                                "Undefined error"
                            )
                        }
                    }
                    is UnknownHostException -> error(
                        HttpState.NO_CONNECTION,
                        null,
                        "No internet connection"
                    )
                    is SocketTimeoutException -> error(
                        HttpState.TIMEOUT,
                        null,
                        "Slow connection"
                    )
                    is IOException -> error(HttpState.BAD_RESPONSE, null, throwable.message)
                    else -> error(HttpState.NOT_DEFINED, null, throwable.message)
                }
            }
        }
    }

    private fun error(cause: HttpState, code: Int?, errorMessage: String?): RemoteResult.Error =
        RemoteResult.Error(cause, code, errorMessage)


    private fun parseHttpError(throwable: HttpException): RemoteResult<Nothing> {
        return try {
            val errorBody = throwable.response()?.errorBody()?.string() ?: "Unknown HTTP error body"
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(BaseResponseDto::class.java)
            val baseDto = jsonAdapter.fromJson(errorBody)
            val errorMessage = baseDto?.message ?: "Error Unknown"
            error(HttpState.CLIENT_ERROR, throwable.code(), errorMessage)
        } catch (exception: Exception) {
            error(HttpState.CLIENT_ERROR, throwable.code(), exception.localizedMessage)
        }
    }
}