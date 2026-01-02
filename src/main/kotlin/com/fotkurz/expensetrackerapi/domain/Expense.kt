package com.fotkurz.expensetrackerapi.domain

import java.time.ZonedDateTime

class Expense(
    val id: Long,
    val title: String,
    val description: String? = null,
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    val expendedAt : ZonedDateTime = ZonedDateTime.now(),
    val amount: Double,
)