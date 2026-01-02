package com.fotkurz.expensetrackerapi.usecase

import com.fotkurz.expensetrackerapi.domain.Expense

interface FindExpensesUsecase {

    fun findAll(): List<Expense>

    fun findOne(id: Long): Expense?
}