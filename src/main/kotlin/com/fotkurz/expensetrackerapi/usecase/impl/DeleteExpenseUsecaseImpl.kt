package com.fotkurz.expensetrackerapi.usecase.impl

import com.fotkurz.expensetrackerapi.persistence.ExpenseJpaRepository
import com.fotkurz.expensetrackerapi.usecase.DeleteExpenseUsecase
import org.springframework.stereotype.Service

@Service
class DeleteExpenseUsecaseImpl (
    private val expenseJpaRepository: ExpenseJpaRepository
): DeleteExpenseUsecase {
    override fun delete(id: Long) =
        expenseJpaRepository.deleteById(id)

}