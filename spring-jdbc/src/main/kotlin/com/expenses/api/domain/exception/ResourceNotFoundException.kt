package com.expenses.api.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException(resourceId: String) : RuntimeException("resource with id <$resourceId> does not exists.")