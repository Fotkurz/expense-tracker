package com.expenses.api.presentation.dto

import com.expenses.api.domain.Expense
import java.time.ZonedDateTime
import java.util.UUID

data class ExpenseResponse(
    val id: UUID? = null,
    val title: String,
    val amount: Double,
    val labels: List<String>,
    val expendedAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime?,
) {

    companion object{
        fun fromDomain(expense: Expense) = ExpenseResponse(
            id = expense.id,
            title = expense.title,
            amount = expense.amount,
            labels = expense.labels ?: emptyList(),
            expendedAt = expense.expendedAt,
            createdAt = expense.createdAt,
            updatedAt = expense.updatedAt,
        )
    }
}