package com.fotkurz.expensetrackerapi.usecase.impl

import com.fotkurz.expensetrackerapi.api.dto.UpdateExpenseRequest
import com.fotkurz.expensetrackerapi.domain.Expense
import com.fotkurz.expensetrackerapi.persistence.ExpenseJpaRepository
import com.fotkurz.expensetrackerapi.usecase.UpdateExpenseUsecase
import org.springframework.stereotype.Service

@Service
class UpdateExpenseUsecaseImpl (
    private val expenseJpaRepository: ExpenseJpaRepository
): UpdateExpenseUsecase {
    override fun update(id: Long, updateExpenseRequest: UpdateExpenseRequest): Expense {
        val found = expenseJpaRepository.findById(id)
        if (!found.isPresent) {
            throw Exception("Expense not found")
        }

        val expense = found.get()

        expense.title = updateExpenseRequest.title ?: expense.title
        expense.description = updateExpenseRequest.description ?: expense.description
        expense.amount = updateExpenseRequest.amount ?: expense.amount
        expense.expendedAt = updateExpenseRequest.expendedAt ?: expense.expendedAt

        expenseJpaRepository.save(expense)

        return Expense(
            id = expense.id!!,
            title = expense.title,
            description = expense.description,
            amount = expense.amount,
            expendedAt = expense.expendedAt,
            createdAt = expense.createdAt
        )
    }
}