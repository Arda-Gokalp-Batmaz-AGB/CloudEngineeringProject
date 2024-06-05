package com.arda.core_api.util

public sealed class Resource<out R> {
    data class Sucess<out R>(val result: R) : Resource<R>()
    data class Failure<out R>(
        val exception: Exception? = null,
        val exceptionList : List<Exception>? = null
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}