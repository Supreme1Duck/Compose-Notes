package com.example.duck.fastnotes.manager

sealed class Result<out T> {

    class Success<T>(val data: T) : Result<T>()

    class Error(val throwable: Throwable) : Result<Nothing>()

    object Loading: Result<Unit>()

    companion object {
        fun <T> success(data: T): Success<T> {
            return Success(data)
        }

        fun <T> error(throwable: Throwable): Error {
            return Error(throwable)
        }

        fun loading() : Loading = Loading
    }
}
