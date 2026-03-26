package com.expenses.api.presentation.dto

data class ErrorResponse(
    val error: HttpRequestError,
    val message: String?,
)

enum class HttpRequestError {
    INTERNAL_SERVER_ERROR,
    NOT_FOUND_ERROR,
}
