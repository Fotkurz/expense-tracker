package com.fotkurz.expensetrackerapi.api.dto

import jakarta.validation.constraints.Size
import java.time.ZonedDateTime

data class UpdateExpenseRequest(
    @field:Size(min = 1, max = 25)
    val title: String? = null,
    val description: String? = null,
    val amount: Double? = null,
    val expendedAt: ZonedDateTime? = null,
)
