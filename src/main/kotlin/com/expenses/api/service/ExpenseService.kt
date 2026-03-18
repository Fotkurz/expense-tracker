package com.expenses.api.service

import com.expenses.api.domain.Expense
import com.expenses.api.repository.ExpenseRepository
import com.expenses.api.repository.entity.ExpenseEntity
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ExpenseService(
    val repo: ExpenseRepository
) {

    fun findById(id: UUID): Expense {
        val possible = repo.findById(id)
        if (possible.isPresent) {
            val expense = possible.get()
            return Expense(
                id = expense.id,
                title = expense.title,
                amount = expense.amount,
                labels = expense.labels,
                expendedAt = expense.expendedAt.toZonedDateTime(),
                createdAt = expense.createdAt.toZonedDateTime(),
                updatedAt = expense.updatedAt?.toZonedDateTime(),
            )
        }

        throw IllegalArgumentException("Expense with id $id does not exist")
    }

    fun create(expense: Expense): UUID {

        try {
            val saved = repo.save(
                ExpenseEntity(
                    title = expense.title,
                    amount = expense.amount,
                    labels = expense.labels!!,
                    expendedAt = expense.expendedAt.toOffsetDateTime(),
                    createdAt = expense.createdAt.toOffsetDateTime(),
                    updatedAt = expense.updatedAt?.toOffsetDateTime(),
                )
            )
            return saved.id!!
        } catch (ex: Exception) {
            // TODO: add more contextualized exception
            throw ex
        }
    }

}