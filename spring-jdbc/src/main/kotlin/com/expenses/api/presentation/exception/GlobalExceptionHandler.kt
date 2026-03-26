package com.expenses.api.presentation.exception

import com.expenses.api.domain.exception.ResourceNotFoundException
import com.expenses.api.presentation.dto.ErrorResponse
import com.expenses.api.presentation.dto.HttpRequestError
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception,
        req: WebRequest
    ): ResponseEntity<in Any>? {
        val err = ErrorResponse(
            error = HttpRequestError.INTERNAL_SERVER_ERROR,
            message = "An unexpected error occurred while processing the request, contact administrator.",
        )

        return super.handleExceptionInternal(
            ex,
            err,
            HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            req
        )
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(e: ResourceNotFoundException, req: WebRequest): ResponseEntity<in Any>? {
        val err = ErrorResponse(
            error = HttpRequestError.NOT_FOUND_ERROR,
            message = e.message,
        )
        return super.handleExceptionInternal(e, err, HttpHeaders(), HttpStatus.NOT_FOUND, req)
    }
}