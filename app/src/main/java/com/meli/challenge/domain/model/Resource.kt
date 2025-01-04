package com.meli.challenge.domain.model

sealed class Resource<T>(
    val data: T? = null,
    val error: NetworkError? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class Failure<T>(networkError: NetworkError): Resource<T>(error = networkError)
    class Loading<T>: Resource<T>()


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Resource<*>) return false
        return when (this) {
            is Success -> other is Success<*> && this.data == other.data
            is Failure -> other is Failure<*> && this.error == other.error
            is Loading -> other is Loading<*>
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is Success -> data?.hashCode() ?: 0
            is Failure -> error?.hashCode() ?: 0
            is Loading -> javaClass.hashCode()
        }
    }
}

data class NetworkError(val code: Int? = null, val message: String)
