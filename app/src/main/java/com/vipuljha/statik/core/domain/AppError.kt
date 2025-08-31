package com.vipuljha.statik.core.domain

sealed interface AppError {
    /** The device has no active internet connection. */
    data object NoInternet : AppError

    /** The network request took too long to complete. */
    data object RequestTimeout : AppError

    /** The user's session has expired or they lack permission for the action. */
    data object Unauthorized : AppError

    /** The server is currently down or unreachable. */
    data object ServiceUnavailable : AppError

    /** The server responded with a specific, non-2xx error code. */
    data class ApiError(val code: Int, val apiMessage: String) : AppError

    /** Failed to parse server data due to an API contract mismatch. */
    data class SerializationError(val originalException: Throwable) : AppError

    /** An error occurred while interacting with the local database. */
    data class DatabaseError(val throwable: Throwable) : AppError

    /** An error occurred during a local file system operation. */
    data class FileIOError(val throwable: Throwable) : AppError

    /** The user has not granted a required system permission. */
    data class PermissionDenied(val permission: String) : AppError

    /** A catch-all for any unexpected or unhandled exception. */
    data class Unknown(val throwable: Throwable) : AppError
}