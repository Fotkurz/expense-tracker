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

    @Throws(IllegalArgumentException::class)
    fun findById(id: UUID): Expense {
        // TODO: findById failing because no converter for java.sql.Timestamp -> ZonedDateTime. must look up why
        // TODO: method is ugly as hell, make it cleaner
        val possible = repo.findById(id)
        if (possible.isPresent) {
            val expense = possible.get()
            return Expense(
                id = expense.id,
                title = expense.title,
                amount = expense.amount,
                expendedAt = expense.expendedAt,
                createdAt = expense.createdAt,
                updatedAt = expense.updatedAt,
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
                    expendedAt = expense.expendedAt,
                    createdAt = expense.createdAt,
                    updatedAt = expense.updatedAt,
                )
            )
            return saved.id!!
        } catch (ex: Exception) {
            // TODO: add more contextualized exception
            throw ex
        }
    }
}