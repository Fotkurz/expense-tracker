package com.expenses.api.usecase

import com.expenses.api.domain.Expense
import com.expenses.api.repository.ExpenseRepository
import com.expenses.api.repository.entity.ExpenseEntity
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class CreateExpenseUseCase(
    val repo: ExpenseRepository
) {

    fun findById(id: UUID): Optional<ExpenseEntity> = repo.findById(id)

    fun create(expense: Expense): UUID {

        try {
            val saved = repo.save(
                ExpenseEntity(
                    title = expense.title,
                    amount = expense.amount,
                    labels = expense.labels!!,
                    expendedAt = expense.expendedAt,
                    createdAt = expense.createdAt,
                    updatedAt = expense.updatedAt,
                )
            )
            return saved.id!!
        } catch (ex: Exception) {
            throw ex
        }
    }
}