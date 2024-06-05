package com.arda.core_api.util

fun <T> Collection<T>.copyOf(): Collection<T> {
    return mutableListOf<T>().also { it.addAll(this) }
}

fun <T> Collection<T>.mutableCopyOf(): MutableCollection<T> {
    return mutableListOf<T>().also { it.addAll(this) }
}