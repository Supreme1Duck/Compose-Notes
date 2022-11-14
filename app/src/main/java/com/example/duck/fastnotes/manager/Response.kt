package com.example.duck.fastnotes.manager

sealed class Response<out T> {

    class Success<T>(val data: T) : Response<T>()

    class Error(val throwable: Throwable?) : Response<Nothing>()

    object Loading: Response<Unit>()

    companion object {
        fun <T> success(data: T): Success<T> {
            return Success(data)
        }

        fun error(throwable: Throwable?): Error {
            return Error(throwable)
        }

        fun loading() : Loading = Loading
    }
}
