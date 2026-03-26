package com.expenses.api.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidException(cause: String) : RuntimeException(cause) {
}