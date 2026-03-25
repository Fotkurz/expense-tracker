package com.expenses.api.presentation.dto

import java.time.ZonedDateTime

data class UpdateExpenseRequest(
    val title: String?,
    val amount: Double?,
    val labels: List<String>?,
    val expendedAt: ZonedDateTime?,
)