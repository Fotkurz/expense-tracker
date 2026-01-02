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
            id = it.id!!,
            title = it.title,
            description = it.description,
            amount = it.amount,
            createdAt = it.createdAt,
            expendedAt = it.expendedAt,
        ) }

    @Throws(Exception::class)
    override fun findOne(id: Long): Expense {
        val data = expensesRepository.findById(id)
        if (data.isPresent) {
            val found = data.get()
            return  Expense(
                id = found.id!!,
                title = found.title,
                description = found.description,
                amount = found.amount,
                createdAt = found.createdAt,
                expendedAt = found.expendedAt,
            )
        } else {
            throw Exception("Expense not found")
        }
    }
}