package com.expenses.api.presentation.dto

data class PaginatedExpenseResponse(
    val size: Int,
    val page: Int,
    val items: List<ExpenseResponse>
)
