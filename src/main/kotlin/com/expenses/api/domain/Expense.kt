package com.expenses.api.domain

import java.time.ZonedDateTime
import java.util.UUID

// TODO: must adjust the date time

class Expense(
    val id: UUID? = null,
    val title: String,
    val amount: Double,
    val labels: List<String>? = listOf(),
    val expendedAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime? = null,
)