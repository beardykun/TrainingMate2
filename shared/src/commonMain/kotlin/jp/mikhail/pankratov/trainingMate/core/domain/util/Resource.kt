package jp.mikhail.pankratov.trainingMate.core.domain.util

sealed class Resource<T>(val data: T?, val error: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: String) : Resource<T>(null, error)
}