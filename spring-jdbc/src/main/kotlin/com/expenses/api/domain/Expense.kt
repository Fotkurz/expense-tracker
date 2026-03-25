package com.expenses.api.domain

import java.time.ZonedDateTime
import java.util.UUID

class Expense(
    val id: UUID? = null,
    val userId: UUID? = UUID.fromString("a2cc64db-b745-4ee1-83e7-27fae887d1c6"),
    val title: String,
    val amount: Double,
    val labels: List<String>? = listOf(),
    val expendedAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime? = null,
)