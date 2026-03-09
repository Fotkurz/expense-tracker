package com.expenses.api.usecase

import com.expenses.api.domain.Expense
import com.expenses.api.repository.ExpenseRepository
import org.springframework.stereotype.Service

@Service
class CreateExpenseUseCase(
    val repo: ExpenseRepository
) {

    fun create(expense: Expense) {
        repo.create(expense)
    }
}