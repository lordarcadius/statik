package com.vipuljha.statik.core.domain

sealed interface Response<out T> {
    data object Loading : Response<Nothing>
    data class Success<out T>(val data: T) : Response<T>
    data class Error(val error: AppError) : Response<Nothing>
}