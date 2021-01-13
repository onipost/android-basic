package com.example.com.data.entity.other

import android.content.Context

abstract class AppError(
    override val message: String,
    val type: ErrorType,
    open val code: Int,
    open val isCritical: Boolean
) : Throwable() {

    enum class ErrorType {
        API_ERROR,
        DB_ERROR,
        OTHER_ERROR
    }

    companion object {

        fun buildApiError(code: Int): ApiError {
            return ApiError(code)
        }

        fun buildApiError(code: ApiError.State): ApiError {
            return ApiError(code.code)
        }

        fun buildDbError(message: String): AppError {
            return DbError(message)
        }

        fun buildError(message: String, isCritical: Boolean = false): AppError {
            return OtherError(message, isCritical)
        }
    }
}

class ApiError(override val code: Int, override val isCritical: Boolean = false) :
    AppError("", ErrorType.API_ERROR, code, isCritical) {

    fun toMessage(context: Context): String {
        // Method for parse api error code to some string
        return ""
    }

    enum class State(val code: Int) {
        // List for possible api error codes
    }
}

class DbError(override val message: String, override val isCritical: Boolean = false) :
    AppError(message, ErrorType.DB_ERROR, -1, isCritical)

class OtherError(override val message: String, override val isCritical: Boolean = false) :
    AppError(message, ErrorType.OTHER_ERROR, -1, isCritical)
