package com.fmyapp.core.data.utils

enum class HttpState {
    NO_CONNECTION,
    TIMEOUT,
    CLIENT_ERROR,
    BAD_RESPONSE,
    SERVER_ERROR,
    NOT_DEFINED,
}