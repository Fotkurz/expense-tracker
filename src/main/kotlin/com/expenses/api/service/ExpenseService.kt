package com.expenses.api.service

import com.expenses.api.domain.Expense
import com.expenses.api.repository.ExpenseRepository
import com.expenses.api.repository.entity.ExpenseEntity
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.UUID

@Service
class ExpenseService(
    val repo: ExpenseRepository
) {

    @Throws(IllegalArgumentException::class)
    fun findById(id: UUID): Expense {
        val possible = repo.findById(id)
        if (possible.isPresent) {
            val expense = possible.get()
            return Expense(
                id = expense.id,
                title = expense.title,
                amount = expense.amount,
                expendedAt = ZonedDateTime.ofLocal(expense.expendedAt.toLocalDateTime(), ZoneId.of("UTC"), ZoneOffset.UTC),
                createdAt = ZonedDateTime.ofLocal(expense.createdAt.toLocalDateTime(), ZoneId.of("UTC"), ZoneOffset.UTC),
                updatedAt = ZonedDateTime.ofLocal(expense.updatedAt?.toLocalDateTime(), ZoneId.of("UTC"), ZoneOffset.UTC)
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
            throw ex
        }
    }
}