package com.fotkurz.expensetrackerapi.usecase.impl

import com.fotkurz.expensetrackerapi.api.dto.CreateExpenseRequest
import com.fotkurz.expensetrackerapi.domain.Expense
import com.fotkurz.expensetrackerapi.persistence.ExpenseJpaRepository
import com.fotkurz.expensetrackerapi.persistence.entity.ExpenseEntity
import com.fotkurz.expensetrackerapi.usecase.CreateExpenseUsecase
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class CreateExpenseUsecaseImpl(
    private val expenseJpaRepository: ExpenseJpaRepository,
) : CreateExpenseUsecase {
    override fun create(createExpenseRequest: CreateExpenseRequest): Expense {
        val new = ExpenseEntity(
            title = createExpenseRequest.title,
            description = createExpenseRequest.description,
            amount = createExpenseRequest.amount,
            expendedAt = createExpenseRequest.expendedAt,
            createdAt = ZonedDateTime.now(),
        )

        val saved = expenseJpaRepository.save(new)

        return Expense(
            id = saved.id!!,
            title = saved.title,
            description = saved.description,
            amount = saved.amount,
            createdAt = saved.createdAt,
            expendedAt = saved.expendedAt,
        )
    }
}