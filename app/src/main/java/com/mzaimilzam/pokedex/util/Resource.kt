package com.mzaimilzam.pokedex.util

/**
 * Created by Muhammad Zaim Milzam on 03/10/2021.
 * linkedin : Muhammad Zaim Milzam
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}