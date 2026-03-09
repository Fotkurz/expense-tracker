package com.expenses.api.repository

import com.expenses.api.domain.Expense
import com.expenses.api.repository.entity.ExpenseEntity
import org.springframework.stereotype.Service

@Service
class ExpenseRepository {
    fun create(expense: Expense): ExpenseEntity {
        println("created")
        return ExpenseEntity()
    }
}
