package com.fmyapp.core.presentation.abstraction

abstract class BaseMapper<in I, out O> {
    abstract fun map(data: I): O
}