package com.fotkurz.expensetrackerapi.usecase.impl

import com.fotkurz.expensetrackerapi.domain.Expense
import com.fotkurz.expensetrackerapi.persistence.ExpenseJpaRepository
import com.fotkurz.expensetrackerapi.usecase.FindExpensesUsecase
import org.springframework.stereotype.Service

@Service
class FindExpensesUsecaseImpl(
    val expensesRepository: ExpenseJpaRepository
): FindExpensesUsecase {
    override fun findAll(): List<Expense> =
        expensesRepository.findAll().map { Expense(
            id = it.id,
            title = it.title,
            description = it.description,
            amount = it.amount,
            createdAt = it.createdAt,
            expendedAt = it.expendedAt,
        ) }

    @Throws(Exception::class)
    override fun findOne(id: Long): Expense =
        expensesRepository.findById(id).ifPresent {
            Expense(
                id = it.id,
                title = it.title,
                description = it.description,
                amount = it.amount,
                createdAt = it.createdAt,
                expendedAt = it.expendedAt,
            )
        }.run {
            throw Exception("Expense not found")
        }

}