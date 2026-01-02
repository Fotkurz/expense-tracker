package com.fotkurz.expensetrackerapi.api.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.ZonedDateTime

data class CreateExpenseRequest(
    @field:NotBlank
    @field:Size(min = 1, max = 25)
    val title: String,
    val description: String? = null,
    @field:NotNull
    val amount: Double,
    @field:NotNull
    val expendedAt: ZonedDateTime
)