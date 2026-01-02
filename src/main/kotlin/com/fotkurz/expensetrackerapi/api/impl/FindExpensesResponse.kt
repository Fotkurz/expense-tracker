package com.fotkurz.expensetrackerapi.api.impl

import com.fotkurz.expensetrackerapi.domain.Expense
import java.time.ZonedDateTime

data class FindExpensesResponse(
    val id: Long,
    val title: String,
    val description: String? = null,
    val amount: Double,
    val createdAt: ZonedDateTime,
    val expendedAt: ZonedDateTime,
) {
    companion object {
        fun fromDomain(domain: Expense): FindExpensesResponse {
            return FindExpensesResponse(
                domain.id,
                domain.title,
                domain.description,
                domain.amount,
                domain.createdAt,
                domain.expendedAt
            )
        }
    }
}