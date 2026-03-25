package com.expenses.api.presentation.dto

import com.expenses.api.domain.Expense
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import java.time.ZonedDateTime
import java.util.UUID

data class CreateExpenseRequest(
    @field:NotEmpty
    @field:Size(min = 1, max = 55)
    val title: String,
    @field:NotNull
    val amount: Double,
    val labels: List<String>? = listOf("OTHER"),
    @field:NotNull
    val expendedAt: ZonedDateTime
) {
        fun toDomain(): Expense =
            Expense(
                title = title,
                amount = amount,
                labels = labels,
                expendedAt = expendedAt,
                createdAt = ZonedDateTime.now(),
            )
}

data class CreateExpenseResponse(
    val id: UUID? = null,
    val title: String,
    val amount: Double,
    val expendedAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime? = null,
) {
    companion object {
        fun fromDomain(expense: Expense) = CreateExpenseResponse(
            id = expense.id,
            title = expense.title,
            amount = expense.amount,
            expendedAt = expense.expendedAt,
            createdAt = expense.createdAt,
            updatedAt = expense.updatedAt,
        )
    }
}